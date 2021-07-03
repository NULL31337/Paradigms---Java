package expression;

import expression.exceptions.EvaluateExeption;
import expression.exceptions.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        TripleExpression expressions = new ExpressionParser().parse("1000000*x*x*x*x*x/(x-1)");
        System.out.println(" x     f");
        for (int i = 0; i <= 10; i++) {
            System.out.print(String.format("%2d     ", i));
            try {
                System.out.println(expressions.evaluate(i, 0, 0));
            } catch (EvaluateExeption e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
