package expression.generic;

import expression.TripleExpressionGeneric;
import expression.operate.*;
import expression.parser.ExpressionParser;


public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        final OperatorType<?> type = switch (mode) {
            case "i" -> new OperateCheckedInt();
            case "d" ->  new OperateDouble();
            case "bi" ->  new OperateBigInteger();
            case "u" ->  new OperateIntegerMod(0);
            case "p" ->  new OperateIntegerMod(1009);
            case "b" ->  new OperateByte();
            default -> throw new RuntimeException("Illegal mode");
        };
        return calculate(type, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] calculate(OperatorType<T> type,
                                       String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        TripleExpressionGeneric<T> exp = new ExpressionParser<T>().parse(expression, type);
        Object[][][] tab = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        tab[i - x1][j - y1][k - z1] = exp.evaluate(type.convert(i),
                                type.convert(j), type.convert(k));
                    } catch (RuntimeException ignored) {}
                }
            }
        }
        return tab;
    }
}
