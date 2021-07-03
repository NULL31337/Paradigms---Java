"use strict";


const operation = {}

function expressionConstructor(toString, argsCount, evaluate, diff, constructor, prefix = toString, postfix = toString) {
    if (argsCount === 0) {
        const exp = constructor.prototype;
        exp.toString = toString;
        exp.evaluate = evaluate;
        exp.prefix = prefix;
        exp.postfix = postfix;
        exp.diff = diff;
    } else {
        constructor = function (...args) {
            AbstractOperation.call(this, ...args);
        }
        constructor.prototype = Object.create(AbstractOperation.prototype);
        const exp = constructor.prototype;
        exp.toStringOp = toString;
        operation[toString] = [constructor, argsCount, toString];
        exp.evaluateOp = evaluate;
        exp.diffOp = diff;
    }
    return constructor;
}

const AbstractOperation = expressionConstructor(
    function() {
        return `${this.args.join(" ")} ${this.toStringOp}`;
    },
    0,
    function (...vars) {
        return this.evaluateOp(...this.args.map(x => x.evaluate(...vars)));
    },
    function (name) {
        return this.diffOp(name, ...this.args);
    },
    function (...args) {
        this.args = args;
    },
    function() {
        return `(${this.toStringOp} ${this.args.map(x => x.prefix()).join(" ")})`;
    },
    function() {
        return `(${this.args.map(x => x.postfix()).join(" ")} ${this.toStringOp})`;
    }
);


const Const = expressionConstructor(
    function () {
        return this.value.toString();
    },
    0,
    function () {
        return this.value
    },
    function () {
        return Const.zero;
    },
    function (value) {
        this.value = value
    }

)

Const.zero = new Const(0);
Const.one = new Const(1);
Const.two = new Const(2);

const Variable = expressionConstructor(
    function() {
        return this.name;
    },
    0,
    function(...args) {
        return args[this.index];
    },
    function (name) {
        return this.name === name ? Const.one : Const.zero;
    },
    function(name) {
        this.name = name
        this.index = Variable[name];
    }
)

Variable.x = 0;
Variable.y = 1;
Variable.z = 2;

const Add = expressionConstructor(
    "+",
    2,
    (a, b) => a + b,
    (name, a, b) => new Add (a.diff(name), b.diff(name)),
);

const Subtract = expressionConstructor(
    "-",
    2,
    (a, b) => a - b,
    (name, a, b) => new Subtract (a.diff(name), b.diff(name)),
);

const Multiply = expressionConstructor(
    "*",
    2,
    (a, b) => a * b,
    (name, a, b) => new Add (new Multiply (a.diff(name), b), new Multiply(b.diff(name), a)),
);

const Divide = expressionConstructor(
    "/",
    2,
    (a, b) => a / b,
    (name, a, b) => new Divide (new Subtract (new Multiply (a.diff(name), b),
        new Multiply(b.diff(name), a)),new Multiply(b, b)),
);

const Negate = expressionConstructor(
    "negate",
    1,
    (a) => -a,
    (name, a) => new Negate(a.diff(name)),
);

const Hypot = expressionConstructor(
    "hypot",
    2,
    (a, b) => a * a + b * b,
    (name, a, b) =>  (new Add(new Multiply(a, a), new Multiply(b, b))).diff(name)
)

const HMean = expressionConstructor(
    "hmean",
    2,
    (a, b) => 2 / ((1 / a) + (1 / b)),
    (name, a, b) => (new Divide(Const.two, new Add(new Divide(Const.one, a), new Divide(Const.one, b)))).diff(name)
)
const ArithMean = expressionConstructor(
    "arith-mean",
    -1,
    (...args) => (args.reduce((a, b) => a + b)) / args.length,
    (name, ...args) => (new Divide(args.reduce((a, b) => new Add (a, b), Const.zero), new Const (args.length))).diff(name)
);

const Sign = expressionConstructor(
    "sign",
    1,
    (a) => ((a < 0) ? -1 : ((a > 0) ? 1 : 0)),
    (name, a) => new Divide(Const.one, Const.two),
);

const Abs = expressionConstructor(
    "abs",
    1,
    (a) => Math.abs(a),
    (name, a) => new Multiply(new Sign(a), a.diff(name)),
);

const Pow = expressionConstructor(
    "pow",
    2,
    (a, n) => (a === 0 && n === 0 ? 0 : a ** n),
    (name, a, n) => (new Multiply(new Multiply(a.diff(name), n), new Pow(a, new Subtract(n, Const.one)))),
);

const GeomMean = expressionConstructor(
    "geom-mean",
    -1,
    (...args) => (Math.abs(args.reduce((a, b) => a * b, 1))) ** (1 / args.length),
    (name, ...args) => new Pow(new Abs(args.reduce((a, b) => new Multiply(a, b), Const.one)),
        new Const(1 / args.length)).diff(name)
);

const HarmMean = expressionConstructor(
    "harm-mean",
    -1,
    (...args) => args.length / (args.reduce((a, b) => a + 1 / b, 0)),
    (name, ...args) => (new Divide(new Const(args.length), (args.reduce((a, b) =>
        new Add(a, new Divide(Const.one, b)), Const.zero)))).diff(name)
);

function parse (expression) {
    let stack = [];
    for (const token of expression.trim().split(/\s+/)) {
        if (token in operation) {
            let oper = operation[token];
            stack.push(new oper[0](...stack.splice(-oper[1])));
        } else if (token in Variable) {
            stack.push(new Variable(token));
        } else if (token in Const) {
            stack.push(new Const[token]);
        } else {
            stack.push(new Const(parseInt(token)));
        }
    }
    return stack.pop();
}

////////////////
///   HW 7   ///
////////////////

function ParserError(message) {
    this.message = message;
}
ParserError.prototype = Object.create(Error.prototype);

function createParserError(func) {
    const constructor = function (...args) {
        ParserError.call(this, func(...args));
        this.pos = args[0];
    }
    constructor.prototype = Object.create(ParserError.prototype);
    return constructor;
}

const BracketError = createParserError(
    (pos, found) => "BracketError: Expected: ')' at pos " + pos + " but found: '" + found + "'");

const EOFError = createParserError(
    (pos, found) => "EOFError: Expected: End of file at pos " + pos + " but found: '" + found + "'");

const ExpectedArgumentError  = createParserError(
    (pos, found) => "ExpectedArgumentError: Expected argument at " + pos + " but found: '" +
        (found === undefined ? "EOF" : found)  + "'");

const UnexpectedArgumentCountError  = createParserError(
    (pos, expected, found, op) => "UnexpectedArgumentCountError: For operation: '" + op + "' expected: " + expected +
        " arguments, but found: " + found+ " at pos " + pos);

const UnsupportedOperationError = createParserError(
    (pos, found) => "UnsupportedOperationError: Expected: operation at pos " + pos + " but found: '" + found + "'");
const ConstOrVariableOperationError = createParserError(
    (pos, found) => "ConstOrVariableOperationError: Expected operation but found " + found +
        " consts or variables at pos: " + pos + " without operation");


function parsePrefix(expression) {
    return parseExpression(expression, "prefix");
}
function parsePostfix(expression) {
    return parseExpression(expression, "postfix");
}

function Parser (expression) {
    const string = expression;
    let pos = 0;
    let nextToken = "";
    let currentChar = string[0];
    let currentToken = "";
    this.getPos = () => {
        return pos;
    }
    this.getNextChar = () => {
        currentChar = string[++pos];
        return currentChar;
    }

    this.skipWhitespace = () => {
        while (currentChar === ' ') {
            this.getNextChar();
        }
    }
    this.getNextToken = () => {
        if (nextToken !== undefined && nextToken.length !== 0) {
            currentToken = nextToken;
            nextToken = "";
            return currentToken;
        }
        this.skipWhitespace();
        currentToken = "";
        while (!([" ", "(", ")"].includes(currentChar)) && !this.eof()) {
            currentToken += currentChar;
            this.getNextChar();
        }
        if (currentToken.length === 0) {
            currentToken = currentChar;
            if (!this.eof()) {
                this.getNextChar();
            }
        }
        return currentToken;
    }
    this.getCurrentToken = () => {
        return currentToken;
    }
    this.viewNextToken = () => {
        nextToken = this.getNextToken();
        return nextToken;
    }
    this.eof = () => {
        return pos === string.length;
    }
}

function parseExpression(expression, mode) {
    const parser = new Parser(expression.trim());
    let ans = parseArgument();
    if (!parser.eof()) {
        throw new EOFError(parser.getPos(), parser.getCurrentToken());
    }
    return ans;
    function parseArgument () {
        let token = parser.getNextToken();
        if (token === "(") {
            let operation = parseOperation();
            if (parser.getNextToken() !== ")") {
                throw new BracketError(parser.getPos(), (parser.eof() ? "EOF" : parser.getCurrentToken()));
            }
            return operation;
        } else if (token in Variable) {
            return new Variable(token);
        } else if (token in Const) {
            return new Const[token];
        } else if (!isNaN(+token)) {
            return new Const(parseInt(token));
        }
        throw new ExpectedArgumentError(parser.getPos(), parser.getCurrentToken());
    }
    function parseOperation() {
        let currentOperator, currentArgs;
        if (mode === "prefix") {
            currentOperator = parseOperator();
            currentArgs = parseArgs();
        } else {
            currentArgs = parseArgs();
            currentOperator = parseOperator();
        }
        if (currentOperator === undefined) {
            if (parser.getCurrentToken() === ")"){
                throw new ConstOrVariableOperationError(parser.getPos(), currentArgs.length);
            }
            throw new UnsupportedOperationError(parser.getPos(), parser.getCurrentToken());
        } else {
            if (currentArgs.length !== currentOperator[1] && currentOperator[1] !== -1) {
                throw new UnexpectedArgumentCountError( parser.getPos(), currentOperator[1],
                    currentArgs.length, currentOperator[2]);
            }
        }
        return new currentOperator[0](...currentArgs);
    }

    function parseOperator() {
        let operator = parser.viewNextToken();
        if (operator in operation) {
            return operation[parser.getNextToken()];
        }
        return undefined;
    }

    function parseArgs() {
        let args = [];
        while (tokenIsArgs()) {
            args.push(parseArgument());
        }
        return args;
    }

    function tokenIsArgs() {
        let token = parser.viewNextToken();
        return !(token in operation) && token !== ")";
    }
}