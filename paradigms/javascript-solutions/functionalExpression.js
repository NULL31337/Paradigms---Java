"use strict";

const operator = (op) => (...args) => (...vars) => op(...args.map(func => func(...vars)));

const cnst = value => () => value;

const VARIABLES = ['x', 'y', 'z'];
const variable = name => (...args) => args[VARIABLES.indexOf(name)];
const vars = {
    'x' : variable('x'),
    'y' : variable('y'),
    'z' : variable('z'),
}

const one = cnst(1);
const two = cnst(2);

const tokenToConst = {
    one: one,
    two: two
}

const add = operator((a, b) => a + b);
const subtract = operator((a, b) => a - b);
const multiply = operator((a, b) => a * b);
const divide = operator((a, b) => a / b);
const negate = operator(a => -a);
const min = operator((...args) => Math.min(...args));
const max = operator((...args) => Math.max(...args));
const madd = operator((a, b, c) => (a * b + c));
const floor = operator((a) => Math.floor(a));
const ceil = operator((a) => Math.ceil(a));

const operation = {
    "+": [add, 2],
    "-": [subtract, 2],
    "*": [multiply, 2],
    "/": [divide, 2],
    "negate": [negate, 1],
    "min5": [min, 5],
    "max3": [max, 3],
    "*+": [madd, 3],
    "madd": [madd, 3],
    "_": [floor, 1],
    "floor": [floor, 1],
    "^": [ceil, 1],
    "ceil": [ceil, 1]
}

function parse (expression) {
    let stack = [];
    for (const token of expression.trim().split(/\s+/)) {
        if (token in operation){
            let oper = operation[token];
            stack.push(oper[0](...stack.splice(-oper[1])));
        } else if (token in vars) {
            stack.push(vars[token]);
        } else if (token in tokenToConst) {
            stack.push(tokenToConst[token]);
        } else {
            stack.push(cnst(parseInt(token)));
        }
    }
    return stack.pop();
}
