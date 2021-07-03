package ticTacToe;


import java.util.Arrays;

public class RombBoard extends MNKBoard {
    public RombBoard(int n, int k, int freeMoveLine) {
        super(n * 2 - 1, n * 2 - 1, k, 2 * (n - 1) * n + 1, freeMoveLine);
        for (int i = 0; i < n; i++){
            Arrays.fill(cells[i], 0, n - i - 1, Cell.B);
            Arrays.fill(cells[2 * n - 2 - i], 0, n - i - 1, Cell.B);
            Arrays.fill(cells[i], n + i, 2 * n - 1, Cell.B);
            Arrays.fill(cells[2 * n - 2 - i], n + i, 2 * n - 1, Cell.B);
        }
    }
    public RombBoard(int n, int k) {
        this(n, k, 2 * n);
    }
}
