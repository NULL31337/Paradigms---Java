(defn numberVec? [v] (every? number? v))
(defn vec? [v] (and (coll? v) (numberVec? v)))
(defn matrix? [m] (and (coll? m) (every? vec? m)))
(defn sameVecSize? [args typeCheck]
  (and
    (every? typeCheck args)
    (every? (partial == (count (first args))) (mapv count (rest args)))))

(defn tensor? [a b] (or (number? a) (number? b) (== (count a) (count b))))


(defn func [typeCheck f]
  (fn [& args] {:pre [(sameVecSize? args typeCheck)]}
    (apply mapv f args)))

(defn funcv [op] (func vec? op))
(defn funcm [op] (func matrix? op))

(def v+ (funcv +))
(def v- (funcv -))
(def v* (funcv *))
(def vd (funcv /))

(defn scalar [& args] {:pre [(sameVecSize? args vec?)]}
  (apply + (apply v* args)))

(defn vect [& args] {:pre [(sameVecSize? args vec?) (== 3 (count (first args)))]}
  (reduce
    #(letfn [(tmpFunc [param1, param2]
               (-
                 (*
                   (nth %1 param1)
                   (nth %2 param2))
                 (*
                   (nth %1 param2)
                   (nth %2 param1))))]
       (vector (tmpFunc 1, 2) (tmpFunc 2, 0) (tmpFunc 0, 1))) args))

(def m+ (funcm v+))
(def m- (funcm v-))
(def m* (funcm v*))
(def md (funcm vd))

(defn v*s [v & args] {:pre [(vec? v) (numberVec? args)]}
  (let [mul (apply * args)]  (mapv #(* % mul) v)))

(defn m*s [m & args] {:pre [(matrix? m) (numberVec? args)]}
 (let [mul (apply * args)] (mapv #(apply v*s % [mul]) m)))

(defn m*v [m v] {:pre [(matrix? m) (vec? v)]}
  (mapv #(scalar % v) m))

(defn transpose [m] {:pre (matrix? m)}
  (apply mapv vector m))

(defn m*m [& args] {:pre [(every? matrix? args)]}
  (reduce #(mapv (partial m*v (transpose %2)) %1) args))


(defn tmp [& t] (conj t))

(defn tensorOperation [op a b] {:pre [(tensor? a b)]}
  (if (number? a)
    (if (number? b)
      (op a b)
      (mapv #(tensorOperation op a %) b))
    (if (number? b)
      (mapv #(tensorOperation op % b) a)
      (mapv #(tensorOperation op %1 %2) a b))))

(defn func2 [op first] (fn ([firstArg] (tensorOperation op first firstArg))
                           ([firstArg & args] (reduce #(tensorOperation op %1 %2) firstArg args))))

(def tb+ (func2 + 0))
(def tb- (func2 - 0))
(def tb* (func2 * 1))
(def tbd (func2 / 1))