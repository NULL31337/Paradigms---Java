package info.kgeorgiy.ja.nagibin.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractWalker {
    protected HashFileVisitor hashFileVisitor;

    AbstractWalker(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("Input must be in the format: java Walk <input file> <output file>");
            return;
        }
        walk(args[0], args[1]);
    }

    private void walk(String inputFile, String outputFile) {
        final Path output, input;

        try {
            input = Paths.get(inputFile);
        } catch (InvalidPathException e) {
            System.err.println("Invalid input path: " + e.getMessage());
            return;
        }
        try {
            output = Paths.get(outputFile);
        } catch (InvalidPathException e) {
            System.err.println("Invalid output path: " + e.getMessage());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(input)) {
            if (output.getParent() != null) {
                Files.createDirectories(output.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(output)) {
                try {
                    hashFileVisitor = new HashFileVisitor(writer);
                } catch (NoSuchAlgorithmException e) {
                    System.err.println("No SHA-1 in MessageDigest" + e.getMessage());
                    return;
                }

                String fileName;
                while ((fileName = reader.readLine()) != null) {
                    try {
                        fileHandler(fileName);
                    } catch (InvalidPathException e) {
                        hashFileVisitor.print(HashFileVisitor.ERROR_HASH, fileName);
                        System.err.println("Invalid path: " + e.getMessage());
                    } catch (IOException e) {
                        hashFileVisitor.print(HashFileVisitor.ERROR_HASH, fileName);
                        System.err.println("Invalid file or directory: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Inaccessible output file: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Invalid input file: " + e.getMessage());
        }
    }

    abstract void fileHandler(String fileName) throws InvalidPathException, IOException;
}
