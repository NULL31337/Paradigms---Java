package ticTacToe;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        IntChecker checker = new IntChecker(in);
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int x = checker.check(), y = checker.check();
            if (x < 0 || y < 0) {
                out.println("Incorrect input");
                continue;
            }
            final Move move = new Move(x, y, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
