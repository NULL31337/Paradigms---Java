package expression.parser;

import expression.*;
import expression.operate.OperatorType;


public class ExpressionParser<T> {

    public TripleExpressionGeneric<T> parse(String source, OperatorType<T> type) {
        return parse(new StringSource(source), type);
    }

    public TripleExpressionGeneric<T> parse(CharSource source, OperatorType<T> type) {
        return new Parser<T>(source, type).parseExpression();
    }

    private static class Parser<T> extends BaseParser {
        private final OperatorType<T> type;
        private AllExpressionGeneric<T> result;
        public Parser(final CharSource source, OperatorType<T> type) {
            super(source);
            this.type = type;
            nextChar();
        }

        private AllExpressionGeneric<T> parseExpression() {
            result = parseAddOrSubtract();
            skipWhitespace();
            if (eof() || test(')')) {
                return result;
            }
            throw error("End of expression expected");
        }

        private AllExpressionGeneric<T> parseAddOrSubtract() {
            result = parseMultiplyOrDivide();
            while (true) {
                skipWhitespace();
                if (test('-')) {
                    result = new Subtract<>(result, parseMultiplyOrDivide(), type);
                } else if (test('+')) {
                    result = new Add<>(result, parseMultiplyOrDivide(), type);
                } else {
                    return result;
                }
            }
        }

        private AllExpressionGeneric<T> parseMultiplyOrDivide() {
            result = parseMod();
            while (true) {
                skipWhitespace();
                if (test('*')) {
                    result = new Multiply<>(result, parseMod(), type);
                } else if (test('/')) {
                    result = new Divide<>(result, parseMod(), type);
                } else {
                    return result;
                }
            }
        }

        private AllExpressionGeneric<T> parseMod() {
            result = parseArgument();
            while (true) {
                skipWhitespace();
                if (test('m') && test('o') && test('d')) {
                    result = new Mod<>(result, parseArgument(), type);
                } else {
                    return result;
                }
            }
        }

        private AllExpressionGeneric<T> parseArgument() {
            skipWhitespace();
            if (test('(')) {
                return parseExpression();
            }
            if (test('-')) {
                if (between('0', '9')) {
                    return tryGetConst("-");
                } else {
                    return new Negate<>(parseArgument(), type);
                }
            }
            String getString = tryGetString();
            if (getString == null) {
                return tryGetConst("");
            } else {
                if (getString.equals("abs")) {
                    return new Abs(parseArgument(), type);
                } else if (getString.equals("square")) {
                    return new Square(parseArgument(), type);
                } else {
                    return new Variable(getString, type);
                }
            }
        }

        private AllExpressionGeneric<T> tryGetConst(String input) {
            StringBuilder sb = new StringBuilder(input);
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
            if (sb.length() == 0) {
                return null;
            } else {
                return new Const<>(sb.toString(), type);
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
