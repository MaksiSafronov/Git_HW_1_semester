package com.mipt.maksimsafronov.IOHWClasses;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;

public class FileProcessor {

    /**
     * Разбивает файл на части указанного размера
     *
     * @param sourcePath путь к исходному файлу
     * @param outputDir  директория для сохранения частей
     * @param partSize   размер каждой части в байтах
     * @return список путей к созданным частям
     */
    public List<Path> splitFile(String sourcePath, String outputDir, int partSize) throws IOException {
        // TODO: Реализовать разбиение файла используя FileChannel и ByteBuffer
        // - Создать части в указанной директории
        // - Имена файлов: originalName.part1, originalName.part2, etc.
        // - Вернуть список путей к созданным частям
        if (partSize <= 0) {
            throw new IllegalArgumentException("Размер части должен быть положительным");
        }

        Path source = Paths.get(sourcePath);
        if (!Files.exists(source)) {
            throw new IOException("Исходный файл не существует: " + sourcePath);
        }

        Path outputDirectory = Paths.get(outputDir);
        if (!Files.exists(outputDirectory)) {
            Files.createDirectories(outputDirectory);
        }

        String originalFileName = source.getFileName().toString();
        List<Path> partPaths = new ArrayList<>();

        try (FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ)) {
            long fileSize = sourceChannel.size();
            long position = 0;
            int partNumber = 1;

            ByteBuffer buffer = ByteBuffer.allocate(partSize);

            while (position < fileSize) {
                String partFileName = originalFileName + ".part" + partNumber;
                Path partPath = outputDirectory.resolve(partFileName);
                partPaths.add(partPath);

                try (FileChannel partChannel = FileChannel.open(partPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    long bytesRemaining = fileSize - position;
                    int bytesToRead = (int) Math.min(partSize, bytesRemaining);

                    buffer.clear();
                    buffer.limit(bytesToRead);

                    sourceChannel.read(buffer, position);
                    buffer.flip();

                    partChannel.write(buffer);
                }

                position += partSize;
                partNumber++;
            }

        }
        return partPaths;
    }
    /**
     * Объединяет части файла обратно в один файл
     * @param partPaths список путей к частям файла (в правильном порядке)
     * @param outputPath путь для результирующего файла
     */
    public void mergeFiles (List <Path> partPaths, String outputPath) throws IOException {
        // TODO: Реализовать объединение частей используя FileChannel
        // - Проверить что все части существуют
        // - Объединить в правильном порядке
        if (partPaths == null || partPaths.isEmpty()) {
            throw new IllegalArgumentException("Список частей не должен быть пустым");
        }

        Path output = Paths.get(outputPath);
        Path outputParent = output.getParent();
        if (outputParent != null && !Files.exists(outputParent)) {
            Files.createDirectories(outputParent);
        }

        for (Path part : partPaths) {
            if (!Files.exists(part)) {
                throw new IOException("Файл части не найден: " + part);
            }
        }
        try (FileChannel outputChannel = FileChannel.open(output, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            for (Path part : partPaths) {
                try (FileChannel partChannel = FileChannel.open(part, StandardOpenOption.READ)) {
                    while (partChannel.read(buffer) != -1) {
                        buffer.flip();
                        outputChannel.write(buffer);
                        buffer.compact();
                    }
                    buffer.flip();
                    if (buffer.hasRemaining()) {
                        outputChannel.write(buffer);
                    }
                    buffer.clear();
                }
            }
        }
    }
}
