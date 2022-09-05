package info.kgeorgiy.ja.nagibin.walk;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Walker extends AbstractWalker{
    Walker(String[] args) {
        super(args);
    }

    @Override
    void fileHandler(String fileName) throws InvalidPathException, IOException {
        hashFileVisitor.print(hashFileVisitor.getHash(Path.of(fileName)), fileName);
    }
}
