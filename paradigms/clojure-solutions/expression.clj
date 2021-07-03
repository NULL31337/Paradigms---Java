(defn variable [key] #(get % key))
(defn constant [value] (constantly value))
(defn make_operation [op] (fn [& args]
                            (fn [variables]
                              (apply op (mapv #(% variables) args)))))

(defn myDivide ([firstArg] (/ 1.0 (double firstArg)))
  ([firstArg & args] (reduce #(/ (double %1) (double %2)) firstArg args)))

(def add (make_operation +))
(def subtract (make_operation -))
(def multiply (make_operation *))
(def divide (make_operation myDivide))
(def negate subtract)
(def square #(* % %))
(def mean_ #(/ (apply + %&) (count %&)))
(def mean (make_operation mean_))
(def varn (make_operation (fn [& args] (- (apply mean_ (mapv square args))
                                          (square (apply mean_ args))))))

(def token_to_op {'+       add
                  '-       subtract
                  '*       multiply
                  '/       divide
                  'negate  negate
                  'mean    mean
                  'varn    varn})


(defn parse [tokens tokenToOp constantRule variableRule]
  (cond
    (list? tokens) (apply (get tokenToOp (first tokens))
                          (mapv #(parse % tokenToOp constantRule variableRule) (rest tokens)))
    (number? tokens) (constantRule tokens)
    (symbol? tokens) (variableRule (str tokens))))

(defn parseFunction [input] (parse (read-string input) token_to_op constant variable))

;;;;;;;;;;;;;;;;;;
;      HW10
;;;;;;;;;;;;;;;;;;

(defn evaluate [this vars] (.IEvaluate this vars))
(defn toString [this] (.IToString this))
(defn diff [this var] (.IDiff this var))

(definterface IExpression
  (IEvaluate [vars])
  (IToString [])
  (IDiff [name]))

(declare ZERO)
(deftype ConstantProto [value]
  IExpression
  (IEvaluate [_ _] value)
  (IToString [_] (str value))
  (IDiff [_ _] ZERO))

(defn Constant [value] (ConstantProto. value))

(def ZERO (ConstantProto. 0))
(def ONE (ConstantProto. 1))
(def TWO (ConstantProto. 2))

(deftype VariableProto [variable]
  IExpression
  (IEvaluate [_ variables] (variables variable))
  (IToString [_] variable)
  (IDiff [_ name] (if (= variable name) ONE ZERO)))

(defn Variable [name] (VariableProto. name))

(deftype OperationProtoConstructor [evaluateRule toStringRule diffRule])

(deftype OperationConstructor [proto args]
  IExpression
  (IEvaluate [_ vars] (apply (.evaluateRule proto)
                             (map #(.IEvaluate % vars) args)))

  (IToString [_] (str "("
                      (.toStringRule proto)
                      " "
                      (clojure.string/join " " (map toString args))
                      ")"))
  (IDiff [_ name] (apply (.diffRule proto) (vector args (map #(.IDiff % name) args)))))


(defn make_op [proto] (fn [& args] (OperationConstructor. proto args)))


;Add, Subtract, Multiply и Divide

(declare Add)
(def AddProto (OperationProtoConstructor. + "+" (fn [_ dArgs]  (apply Add dArgs))))
(def Add (make_op AddProto))

(declare Subtract)
(def SubtractProto (OperationProtoConstructor. - "-" (fn [_ dArgs] (apply Subtract dArgs))))
(def Subtract (make_op SubtractProto))

(declare Multiply)
(defn diff_multiply [args dArgs] (second (reduce
                                           (fn [[x dx] [y dy]] (vector (Multiply x y)
                                                                       (Add (Multiply dy x) (Multiply dx y))))
                                           (mapv vector args dArgs))))

(def MultiplyProto (OperationProtoConstructor. * "*" diff_multiply))
(def Multiply (make_op MultiplyProto))

(declare Divide)
(declare Negate)

(defn square_obj [args] (Multiply args args))

(defn diff_divide [[x & rest_x] [dx & rest_dx]] (if (empty? rest_x)
                                                  (Negate (Divide dx (Multiply x x)))
                                                  (let [multiply_rest_x (apply Multiply rest_x)]
                                                    (Divide (Subtract (Multiply dx multiply_rest_x)
                                                                      (Multiply x (diff_multiply rest_x rest_dx)))
                                                            (square_obj multiply_rest_x)))))


(def DivideProto (OperationProtoConstructor. myDivide "/" diff_divide))
(def Divide (make_op DivideProto))

(def NegateProto (OperationProtoConstructor. - "negate" (fn [_ dArgs] (apply Negate dArgs))))
(def Negate  (make_op NegateProto))

(declare ArithMean)
(defn arith-mean [& args] (/ (apply + args) (count args)))
(def ArithMeanProto (OperationProtoConstructor.
                      arith-mean
                      "arith-mean"
                      (fn [_ dArgs] (apply ArithMean dArgs))))
(def ArithMean (make_op ArithMeanProto));

(declare GeomMean)
(defn geom-mean [& args] (Math/pow (Math/abs (apply * args)) (myDivide (count args))))
(def GeomMeanProto (OperationProtoConstructor.
                     geom-mean
                     "geom-mean"
                     (fn [args dArgs] (Multiply (Divide (diff_multiply args dArgs)
                                                        (Constant (count args)))
                                                (Divide (apply GeomMean args)
                                                        (apply Multiply args))))))

(def GeomMean (make_op GeomMeanProto))

(defn harm-mean [& args] (myDivide (count args) (apply + (mapv #(myDivide %) args))))
(declare HarmMean)
(def HarmMeanProto (OperationProtoConstructor.
                     harm-mean
                     "harm-mean"
                     (fn [args dArgs]
                       (Divide (Multiply (Constant (count args))
                                         (apply Add (mapv (fn [[x dx]] (Divide dx (Multiply x x)))
                                                          (mapv vector args dArgs))))
                               (square_obj (apply Add (mapv #(Divide %) args)))))))
(def HarmMean (make_op HarmMeanProto))

(def operationToObject {'+ Add
                        '- Subtract
                        '* Multiply
                        '/ Divide
                        'negate Negate
                        'arith-mean ArithMean
                        'geom-mean GeomMean
                        'harm-mean HarmMean})

(defn parseObject [input] (parse (read-string input) operationToObject Constant Variable))