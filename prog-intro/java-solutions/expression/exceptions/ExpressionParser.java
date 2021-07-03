package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringSource;

public class ExpressionParser implements Parser {

    @Override
    public TripleExpression parse(String source) {
        return parse(new StringSource(source));
    }

    public static TripleExpression parse(CharSource source) {
        return new Parser(source).parseExpression();
    }

    private static class Parser extends BaseParser {
        public Parser(final CharSource source) {
            super(source);
            nextChar();
        }

        private AllExpressions parseExpression() {
            AllExpressions result = parseGcdLcm();
            skipWhitespace();
            if (eof()) {
                return result;
            }
            throw error("End of expression expected");
        }


        private AllExpressions parseGcdLcm(){
            AllExpressions result = parseAddOrSubtract();
            while (true) {
                String input = tryGetString();
                if (input == null) {
                    return result;
                }
                if (input.equals("gcd")){
                    result = new Gcd(result, parseAddOrSubtract());
                } else if (input.equals("lcm")){
                    result = new Lcm(result, parseAddOrSubtract());
                } else {
                    throw new IllegalFunctionException(input);
                }
            }
        }

        private AllExpressions parseAddOrSubtract() {
            AllExpressions result = parseMultiplyOrDivide();
            while (true) {
                skipWhitespace();
                if (test('-')) {
                    result = new CheckedSubtract(result, parseMultiplyOrDivide());
                } else if (test('+')) {
                    result = new CheckedAdd(result, parseMultiplyOrDivide());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseMultiplyOrDivide() {
            AllExpressions result = parseArgument();
            while (true) {
                skipWhitespace();
                if (test('*')) {
                    result = new CheckedMultiply(result, parseArgument());
                } else if (test('/')) {
                    result = new CheckedDivide(result, parseArgument());
                } else {
                    return result;
                }
            }
        }

        private AllExpressions parseArgument() {
            skipWhitespace();
            if (test('(')) {
                AllExpressions result = parseGcdLcm();
                expect(')');
                return result;
            }
            skipWhitespace();
            if (test('-')) {
                skipWhitespace();
                if (between('0', '9')) {
                    return new Const(tryGetConst(tryGetNumber("-")));
                } else {
                    return new Negate(parseArgument());
                }
            }
            String input = tryGetNumber("");
            if (input == null) {
                input = tryGetString();
            } else {
                return new Const(tryGetConst(input));
            }
            if (input == null) {
                throw new IllegalArgumentException();
            } else if (input.equals("sqrt")) {
                return new Sqrt(parseArgument());
            } else if (input.equals("abs")) {
                return new Abs(parseArgument());
            } else if (input.equals("x") || input.equals("y") || input.equals("z")) {
                return new Variable(input);
            }
            throw new UnknownVariableOrFunctionException(input);
        }

        private String tryGetNumber(String input) {
            StringBuilder sb = new StringBuilder(input);
            while (between('0', '9')){
                sb.append(ch);
                nextChar();
            }
            if (sb.length() == 0) {
                return null;
            }
            return sb.toString();
        }

        private String tryGetString() {
            StringBuilder sb = new StringBuilder();
            while (between('a', 'z') || between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
            if (sb.length() == 0) {
                return null;
            } else {
                return sb.toString();
            }
        }

        private int tryGetConst (String input){
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Illegal number");
            }
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t'));
        }
    }
}
