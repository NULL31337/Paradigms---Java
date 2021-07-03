package ticTacToe;

import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final Game game = new Game(true, new SequentialPlayer(), new RandomPlayer());
        Result result;
        do {
            result = game.play(new RombBoard(100, 3, 3));
            System.out.println("Game result: " + result.toString());
        } while (result != Result.DRAW);
    }
}
