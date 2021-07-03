package expression;

import expression.generic.GenericTabulator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(Arrays.deepToString(new GenericTabulator().tabulate(args[0].substring(1), args[1],
                    Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]),
                    Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
