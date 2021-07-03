package ticTacToe;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MNKBoard implements Board, Position {

    protected final int n, m, k, freeMoveLine;
    protected int countOfTurns;

    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.B, ' '
    );

    protected final Cell[][] cells;
    private Cell turn;

    public MNKBoard(int n, int m, int k, int countOfTurns, int freeMoveLine) {
        if (Math.max(n, m) < k || n <= 0 || m <= 0){
            System.out.println("Incorrect input");
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.m = m;
        this.k = k;
        this.freeMoveLine = freeMoveLine;
        this.countOfTurns = countOfTurns;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }
    public MNKBoard(int n, int m, int k) {
        this(n, m, k, n * m, Math.max(n, m));
    }

    public int getN(){
        return n;
    }

    public int getM(){
        return m;
    }

    @Override
    public Position getPosition() {
        return new ProxyPosition(this);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    public int check(int nowX, int nowY, int changeX, int changeY) {
        int countInLine = 0;
        int x = nowX;
        int y = nowY;
        while (0 <= x && x < n && 0 <= y && y < m && cells[nowX][nowY] == cells[x][y] && countInLine < k) {
            x += changeX;
            y += changeY;
            countInLine++;
        }
        return countInLine;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        Cell now = move.getValue();
        int x = move.getRow(), y = move.getColumn();
        cells[move.getRow()][move.getColumn()] = now;
        countOfTurns--;
        int maxInLine = Math.max(check(x, y, 1, 1) + check(x, y, -1, -1),
                        Math.max(check(x, y, 1, -1) + check(x, y, -1, 1),
                        Math.max(check(x, y, 0, 1) + check(x, y, 0, -1),
                                check(x, y, 1, 0) + check(x, y, -1, 0) - 1))) - 1;

        if (maxInLine >= k) {
            return Result.WIN;
        }
        if (countOfTurns == 0) {
            return Result.DRAW;
        }
        if (maxInLine >= freeMoveLine){
            return Result.EXTRAMOVE;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getColumn() && move.getColumn() < m
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        Integer siz = (Integer.toString(n).length());
        StringBuilder sb = new StringBuilder(" ".repeat(siz + 1));
        for (int i = 0; i < m; i++) {
            sb.append(String.format("%" + siz + "d ", i));
        }
        for (int r = 0; r < n; r++) {
            sb.append(String.format("\n%" + siz + "d ", r));
            for (int c = 0; c < m; c++) {
                sb.append(String.format("%" + siz + "c ", SYMBOLS.get(cells[r][c])));
            }
        }
        return sb.toString();
    }
}
