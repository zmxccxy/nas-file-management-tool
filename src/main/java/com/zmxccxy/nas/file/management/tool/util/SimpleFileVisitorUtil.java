package com.zmxccxy.nas.file.management.tool.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitorUtil extends SimpleFileVisitor<Path> {

    private int dicSuccessCount = 0;
    private int fileSuccessCount = 0;
    private int failCount = 0;
    private String failCase = "";

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        // 文件夹
        boolean deleted = Files.deleteIfExists(dir);
        if (deleted) {
            dicSuccessCount++;
        }
        return super.postVisitDirectory(dir, exc);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // 文件
        boolean deleted = Files.deleteIfExists(file);
        if (deleted) {
            fileSuccessCount++;
        }
        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        // 删除失败
        failCount++;
        failCase = failCase + exc.getLocalizedMessage() + "；";
        return super.visitFileFailed(file, exc);
    }

    public int getDicSuccessCount() {
        return dicSuccessCount;
    }

    public int getFileSuccessCount() {
        return fileSuccessCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public String getFailCase() {
        return failCase;
    }

}
