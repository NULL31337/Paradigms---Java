package ticTacToe;

public class ProxyPosition implements Position {
    private final Position position;
    public ProxyPosition (Position position) {
        this.position = position;
    }

    @Override
    public int getM() {
        return position.getM();
    }
    @Override
    public int getN() {
        return position.getN();
    }
    @Override
    public boolean isValid(Move move){
        return position.isValid(move);
    }
    @Override
    public Cell getCell (int r, int c) {
        return position.getCell(r, c);
    }
    @Override
    public String toString() {
        return position.toString();
    }
}
