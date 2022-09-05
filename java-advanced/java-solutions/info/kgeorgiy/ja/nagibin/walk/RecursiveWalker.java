package info.kgeorgiy.ja.nagibin.walk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class RecursiveWalker extends AbstractWalker {
    RecursiveWalker(String[] args) {
        super(args);
    }

    @Override
    void fileHandler(String fileName) throws InvalidPathException, IOException {
        Files.walkFileTree(Path.of(fileName), hashFileVisitor);
    }
}
