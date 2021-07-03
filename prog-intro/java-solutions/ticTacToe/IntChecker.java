package ticTacToe;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class IntChecker {
    private final Scanner scanner;
    public IntChecker (Scanner scanner) {
        this.scanner = scanner;
    }
    int check() {
        if (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                scanner.next();
                return -1;
            }
        } else {
            throw new NoSuchElementException();//NOTE: use another ex
        }
    }
}
