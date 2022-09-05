package info.kgeorgiy.ja.nagibin.walk;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashFileVisitor extends SimpleFileVisitor<Path> {
    private final BufferedWriter fileWriter;
    private final MessageDigest sha1;

    HashFileVisitor(BufferedWriter fileWriter) throws NoSuchAlgorithmException {
        this.fileWriter = fileWriter;
        sha1 = MessageDigest.getInstance("SHA-1");
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        print(getHash(file), file.toString());
        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        print(ERROR_HASH, file.toString());
        return FileVisitResult.CONTINUE;
    }

    protected String getHash(Path file) {
        try (InputStream reader = new BufferedInputStream(Files.newInputStream(file))) {
            sha1.reset();
            final byte[] buffer = new byte[BUFFER_SIZE];
            int size;
            while ((size = reader.read(buffer)) >= 0) {
                sha1.update(buffer, 0, size);
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
            return ERROR_HASH;
        }
        return HexFormat.of().formatHex(sha1.digest());
    }

    protected void print(String hash, String fileName) throws IOException {
        fileWriter.write(String.format("%s %s", hash, fileName));
        fileWriter.newLine();
    }

    protected static final String ERROR_HASH = String.format("%040x", 0);
    protected static final int BUFFER_SIZE = 2048;
}
