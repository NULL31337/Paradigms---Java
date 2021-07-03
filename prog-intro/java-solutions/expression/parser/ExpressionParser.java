package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {

    @Override
    public TripleExpression parse(String source) {
        return parse(new StringSource(source));
    }

    public static TripleExpression parse(CharSource source) {
        return new Parser(source).parseExpression();
    }

    private static class Parser extends BaseParser {
        private AllExpressions result;
        public Parser(final CharSource source) {
            super(source);
            nextChar();
        }

        private AllExpressions parseExpression() {
            result = parseOr();
            skipWhitespace();
            if (eof() || test(')')) {
                return result;
            }
            throw error("End of expression expected");
        }

        private AllExpressions parseOr() {
            result = parseXor();
            while (true) {
                skipWhitespace();
                if (test('|')) {
                    result = new Or(result, parseXor());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseXor() {
            result = parseAnd();
            while (true) {
                skipWhitespace();
                if (test('^')) {
                    result = new Xor(result, parseAnd());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseAnd() {
            result = parseAddOrSubtract();
            while (true) {
                skipWhitespace();
                if (test('&')) {
                    result = new And(result, parseAddOrSubtract());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseAddOrSubtract() {
            result = parseMultiplyOrDivide();
            while (true) {
                skipWhitespace();
                if (test('-')) {
                    result = new Subtract(result, parseMultiplyOrDivide());
                } else if (test('+')) {
                    result = new Add(result, parseMultiplyOrDivide());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseMultiplyOrDivide() {
            result = parseArgument();
            while (true) {
                skipWhitespace();
                if (test('*')) {
                    result = new Multiply(result, parseArgument());
                } else if (test('/')) {
                    result = new Divide(result, parseArgument());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseArgument() {
            skipWhitespace();
            if (test('(')) {
                return parseExpression();
            }
            if (test('-')) {
                if (between('0', '9')) {
                    return tryGetConst("-");
                } else {
                    return new Negate(parseArgument());
                }
            }
            String getString = tryGetString();
            if (getString == null) {
                return tryGetConst("");
            } else {
                if (getString.equals("low")) {
                    return new Low(parseArgument());
                } else if (getString.equals("flip")) {
                    return new Flip(parseArgument());
                } else {
                    return new Variable(getString);
                }
            }
        }

        private AllExpressions tryGetConst(String input) {
            StringBuilder sb = new StringBuilder(input);
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
            if (sb.length() == 0) {
                return null;
            } else {
                return new Const(Integer.parseInt(sb.toString()));
            }
        }

        private String tryGetString() {
            StringBuilder sb = new StringBuilder();
            while (between('a', 'z')) {
                sb.append(ch);
                nextChar();
            }
            if (sb.length() == 0) {
                return null;
            } else {
                return sb.toString();
            }
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                // skip
            }
        }
    }
}
