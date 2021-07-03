package ticTacToe;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final boolean log;
    private final Player player1, player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        if (player1 == null || player2 == null){
            System.out.println("Incorrect input");
            throw new IllegalArgumentException();
        }
        this.player1 = player1;
        this.player2 = player2;
    }

    public Result play(Board board) {
        while (true) {
            Result result1;
            do {
                result1 = move(board, player1, 1);
                if (result1 != Result.UNKNOWN && result1 != Result.EXTRAMOVE ) {
                    return result1;
                }
            } while (result1 == Result.EXTRAMOVE);
            Result result2;
            do {
                result2 = move(board, player2, 2);
                if (result2 != Result.UNKNOWN && result2 != Result.EXTRAMOVE) {
                    return result2;
                }
            } while (result2 == Result.EXTRAMOVE);
        }
    }

    private Result move(final Board board, final Player player, final int no) {
        final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);

        if (result == Result.DRAW) {
            log("Draw");
        } else if (result != Result.UNKNOWN) {
            log("Player " + no + " " + result.toString());
        }
        return result;
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
