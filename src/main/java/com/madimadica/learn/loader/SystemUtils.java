package com.madimadica.learn.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class SystemUtils {

    /**
     * List the immediate directories in the given location (does not recursively walk the tree)
     * @param dir Parent directory
     * @return List of directories inside {@code dir}
     */
    public static List<Path> listDirectories(Path dir) {
        try (var paths = Files.list(dir)) {
            return paths.filter(Files::isDirectory).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * List the immediate files in the given location (does not recursively walk the tree)
     * @param dir Parent directory
     * @return List of files inside {@code dir}
     */
    public static List<Path> listFiles(Path dir) {
        try (var stream = Files.list(dir)) {
            return stream.filter(Files::isRegularFile).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read a text file from the given path
     * @param path file path to a text-based file
     * @return String of raw file contents.
     */
    public static String readFile(Path path) {
        try {
            if (Files.exists(path) && Files.isRegularFile(path)) {
                return Files.readString(path);
            } else {
                throw new IllegalArgumentException("Not a file: " + path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDirName(Path path) {
        return path.getFileName().toString();
    }

    public static boolean isFileType(Path path, String type) {
        return path.toString().toLowerCase().endsWith(type.toLowerCase());
    }

    public static void deleteDirectoryRecursive(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                giveAllPermissions(file);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                giveAllPermissions(dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void giveAllPermissions(Path path) {
        File f = path.toFile();
        f.setReadable(true, false);
        f.setWritable(true, false);
        f.setExecutable(true, false);
    }

    /**
     * Non-recursively copy all files from the input directory into the output
     */
    public static void copyDirectoryFiles(Path inputDir, Path outputDir) throws IOException {
        // Make sure output directory exists
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }
        try (var stream = Files.list(inputDir)) {
            stream
                    .filter(Files::isRegularFile)
                    .forEach(sourcePath -> {
                        Path targetPath = outputDir.resolve(sourcePath.getFileName());
                        try {
                            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to copy " + sourcePath + " to " + targetPath, e);
                        }
                    });
        }
    }
}
