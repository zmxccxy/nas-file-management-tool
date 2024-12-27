package com.zmxccxy.nas.file.management.tool.controller;

import com.zmxccxy.nas.file.management.tool.enums.MimeEnum;
import com.zmxccxy.nas.file.management.tool.pojo.CommonRequest;
import com.zmxccxy.nas.file.management.tool.pojo.CommonResponse;
import com.zmxccxy.nas.file.management.tool.pojo.FileInfo;
import com.zmxccxy.nas.file.management.tool.util.SimpleFileVisitorUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @PostMapping("/v1/duplicate")
    public CommonResponse<List<FileInfo>> duplicate(@RequestBody CommonRequest commonRequest) throws InterruptedException, IOException {
        String deletePath = commonRequest.getDeletePath();
        Assert.hasText(deletePath, "缺少文件/文件夹的删除路径");

        File deleteFile = new File(deletePath);
        Assert.isTrue(deleteFile.exists(), "不存在");
        Assert.isTrue(deleteFile.canRead(), "没有读权限");
        Assert.isTrue(deleteFile.canWrite(), "没有写权限");

        SimpleFileVisitorUtil fileVisitorUtil = new SimpleFileVisitorUtil();
        Files.walkFileTree(deleteFile.toPath(), fileVisitorUtil);

        return CommonResponse.success(
                String.format("删除了【%s】个文件，【%s】个删除失败%s",
                        fileVisitorUtil.getFileSuccessCount(),
                        fileVisitorUtil.getFailCount(),
                        StringUtils.hasText(fileVisitorUtil.getFailCase()) ? "（" + fileVisitorUtil.getFailCase() + "）" : ""
                ),
                null);
    }

    @PostMapping("/v1/duplicateQuery")
    public CommonResponse<List<FileInfo>> duplicateQuery(@RequestBody CommonRequest commonRequest) throws InterruptedException, IOException {
        String duplicateQueryPath = commonRequest.getDuplicateQueryPath();
        Assert.hasText(duplicateQueryPath, "请输入路径");

        File dic = new File(duplicateQueryPath);
        Assert.isTrue(dic.exists(), "不存在");
        Assert.isTrue(dic.isDirectory(), "不是文件夹");
        Assert.isTrue(dic.canRead(), "没有读权限");
        Assert.isTrue(dic.canWrite(), "没有写权限");

        List<FileInfo> list = new ArrayList<>();
        int allDicCount = 0;
        int allFileCount = 0;

        processFiles(dic, list);

        List<FileInfo> results = new ArrayList<>();
        Map<String, List<FileInfo>> sha256GroupMap = list.stream()
                .filter(fileInfo -> StringUtils.hasText(fileInfo.getSha256()))
                .collect(Collectors.groupingBy(FileInfo::getSha256));

        for (Map.Entry<String, List<FileInfo>> entry : sha256GroupMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                results.addAll(entry.getValue());
            }
        }

        results.sort(Comparator.comparing(FileInfo::getUpdateTime).reversed());

        return CommonResponse.success(String.format("路径下有【%s】个文件夹【%s】个文件，【%s】个重复文件", allDicCount, allFileCount, results.size()), results);
    }

    @PostMapping("/v1/delete")
    public CommonResponse<List<FileInfo>> delete(@RequestBody CommonRequest commonRequest) throws InterruptedException, IOException {
        String deletePath = commonRequest.getDeletePath();
        Assert.hasText(deletePath, "缺少文件/文件夹的删除路径");

        File deleteFile = new File(deletePath);
        Assert.isTrue(deleteFile.exists(), "不存在");
        Assert.isTrue(deleteFile.canRead(), "没有读权限");
        Assert.isTrue(deleteFile.canWrite(), "没有写权限");

        SimpleFileVisitorUtil fileVisitorUtil = new SimpleFileVisitorUtil();
        Files.walkFileTree(deleteFile.toPath(), fileVisitorUtil);

        return CommonResponse.success(
                String.format("删除了【%s】个文件夹【%s】个文件，【%s】个删除失败%s",
                        fileVisitorUtil.getDicSuccessCount(),
                        fileVisitorUtil.getFileSuccessCount(),
                        fileVisitorUtil.getFailCount(),
                        StringUtils.hasText(fileVisitorUtil.getFailCase()) ? "（" + fileVisitorUtil.getFailCase() + "）" : ""
                ),
                null);
    }

    @PostMapping("/v1/deleteQuery")
    public CommonResponse<List<FileInfo>> deleteQuery(@RequestBody CommonRequest commonRequest) throws InterruptedException, IOException {
        String deleteQueryPath = commonRequest.getDeleteQueryPath();
        Assert.hasText(deleteQueryPath, "请输入路径");

        File dic = new File(deleteQueryPath);
        Assert.isTrue(dic.exists(), "不存在");
        Assert.isTrue(dic.isDirectory(), "不是文件夹");
        Assert.isTrue(dic.canRead(), "没有读权限");
        Assert.isTrue(dic.canWrite(), "没有写权限");

        File[] files = dic.listFiles();
        Assert.notNull(files, "没有获取到路径下的内容");
        Assert.noNullElements(files, "路径下的内容为空");

        List<FileInfo> results = new ArrayList<>();
        int allDicCount = 0;
        int allFileCount = 0;
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setOldFileName(file.getName());
            fileInfo.setFileName(file.getName());
            fileInfo.setFilePath(file.getAbsolutePath());
            String fileSize;
            if (file.isFile()) {
                fileSize = String.format("%.2fMB", FileUtils.sizeOf(file) / 1024.0 / 1024.0);
                fileInfo.setFileType(MimeEnum.getExplainByMimeType(Files.probeContentType(file.toPath())));
                allFileCount++;
            } else {
                fileSize = String.format("%.2fMB", FileUtils.sizeOfDirectory(file) / 1024.0 / 1024.0);
                fileInfo.setFileType("文件夹");
                allDicCount++;
            }
            fileInfo.setFileSize(fileSize);
            fileInfo.setUpdateTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
            fileInfo.setCanRead(file.canRead());
            fileInfo.setCanWrite(file.canWrite());
            results.add(fileInfo);
        }

        results.sort(Comparator.comparing(FileInfo::getFileSize));

        return CommonResponse.success(String.format("路径下有【%s】个文件夹【%s】个文件", allDicCount, allFileCount), results);
    }

    @PostMapping("/v1/replaceQuery")
    public CommonResponse<List<FileInfo>> replaceQuery(@RequestBody CommonRequest commonRequest) throws InterruptedException, IOException {
        String path = commonRequest.getPath();
        Assert.hasText(path, "请输入路径");

        File dic = new File(path);
        Assert.isTrue(dic.exists(), "不存在");
        Assert.isTrue(dic.isDirectory(), "不是文件夹");
        Assert.isTrue(dic.canRead(), "没有读权限");
        Assert.isTrue(dic.canWrite(), "没有写权限");

        File[] files = dic.listFiles();
        Assert.notNull(files, "没有获取到路径下的内容");
        Assert.noNullElements(files, "路径下的内容为空");

        List<FileInfo> results = new ArrayList<>();
        int allDicCount = 0;
        int allFileCount = 0;
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setOldFileName(file.getName());
            fileInfo.setFileName(file.getName());
            String fileSize;
            if (file.isFile()) {
                fileSize = String.format("%.2fMB", FileUtils.sizeOf(file) / 1024.0 / 1024.0);
                fileInfo.setFileType(MimeEnum.getExplainByMimeType(Files.probeContentType(file.toPath())));
                allFileCount++;
            } else {
                fileSize = String.format("%.2fMB", FileUtils.sizeOfDirectory(file) / 1024.0 / 1024.0);
                fileInfo.setFileType("文件夹");
                allDicCount++;
            }
            fileInfo.setFileSize(fileSize);
            fileInfo.setUpdateTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
            fileInfo.setCanRead(file.canRead());
            fileInfo.setCanWrite(file.canWrite());
            results.add(fileInfo);
        }

        results.sort(Comparator.comparing(FileInfo::getUpdateTime).reversed());

        return CommonResponse.success(String.format("路径下有【%s】个文件夹【%s】个文件", allDicCount, allFileCount), results);
    }

    @PostMapping("/v1/replace")
    public CommonResponse<List<FileInfo>> replace(@RequestBody CommonRequest commonRequest) throws InterruptedException, IOException {
        String path = commonRequest.getPath();
        Assert.hasText(path, "请输入路径");
        String from = commonRequest.getFrom();
        Assert.notNull(from, "被替换的内容");
        String to = commonRequest.getTo();
        Assert.notNull(to, "替换的内容");

        File dic = new File(path);
        Assert.isTrue(dic.exists(), "不存在");
        Assert.isTrue(dic.isDirectory(), "不是文件夹");
        Assert.isTrue(dic.canRead(), "没有读权限");
        Assert.isTrue(dic.canWrite(), "没有写权限");

        File[] files = dic.listFiles();
        Assert.notNull(files, "没有获取到路径下的内容");
        Assert.noNullElements(files, "路径下的内容为空");

        List<FileInfo> results = new ArrayList<>();
        List<String> replaceFileTypes = commonRequest.getReplaceFileTypes();
        boolean all = replaceFileTypes == null || replaceFileTypes.isEmpty();
        List<String> replaceFileTypesUpperCase = Collections.emptyList();
        if (!all) {
            replaceFileTypesUpperCase = replaceFileTypes.stream().map(replaceFileType -> replaceFileType.toUpperCase(Locale.ROOT)).toList();
        }
        int allDicCount = 0;
        int allFileCount = 0;
        int replaceFailCount = 0;
        int replaceSuccessCount = 0;
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getName());
            String fileSize;
            if (file.isFile()) {
                fileSize = String.format("%.2fMB", FileUtils.sizeOf(file) / 1024.0 / 1024.0);
                fileInfo.setFileType(MimeEnum.getExplainByMimeType(Files.probeContentType(file.toPath())));
                allFileCount++;
            } else {
                fileSize = String.format("%.2fMB", FileUtils.sizeOfDirectory(file) / 1024.0 / 1024.0);
                fileInfo.setFileType("文件夹");
                allDicCount++;
            }
            fileInfo.setFileSize(fileSize);
            fileInfo.setUpdateTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
            fileInfo.setCanRead(file.canRead());
            fileInfo.setCanWrite(file.canWrite());
            results.add(fileInfo);

            String fileName = file.getName();
            String name;
            String extension;
            File newFile;
            if (file.isFile()) {
                int lastIndexOf = fileName.lastIndexOf(".") == -1 ? fileName.length() : fileName.lastIndexOf(".");
                name = fileName.substring(0, lastIndexOf);
                extension = fileName.substring(lastIndexOf);
                newFile = new File(file.getParent(), name.replace(from, to) + extension);
            } else {
                extension = "";
                name = fileName;
                newFile = new File(file.getParent(), name.replace(from, to));
            }
            if (!name.contains(from)) {
                continue;
            }

            if (all || replaceFileTypesUpperCase.contains(extension.toUpperCase(Locale.ROOT))) {
                boolean renameTo = file.renameTo(newFile);
                if (renameTo) {
                    replaceSuccessCount++;
                    fileInfo.setOldFileName(fileName);
                    fileInfo.setFileName(newFile.getName());
                } else {
                    replaceFailCount++;
                    fileInfo.setOldFileName("操作系统不支持，命名失败！");
                    fileInfo.setFileName(fileName);
                }
            }
        }

        results.sort(Comparator.comparing(FileInfo::getUpdateTime).reversed());

        return CommonResponse.success(String.format("路径下有【%s】个文件夹【%s】个文件，对【%s】个文件/文件夹名替换成功，【%s】替换失败！", allDicCount, allFileCount, replaceSuccessCount, replaceFailCount), results);
    }

    @PostMapping("/v1/createMoveQuery")
    public CommonResponse<List<FileInfo>> createMoveQuery(@RequestBody CommonRequest CommonRequest) throws InterruptedException, IOException {
        String createMovePath = CommonRequest.getCreateMovePath();
        Assert.hasText(createMovePath, "请输入路径");

        File dic = new File(createMovePath);
        Assert.isTrue(dic.exists(), "不存在");
        Assert.isTrue(dic.isDirectory(), "不是文件夹");
        Assert.isTrue(dic.canRead(), "没有读权限");
        Assert.isTrue(dic.canWrite(), "没有写权限");

        File[] files = dic.listFiles();
        Assert.notNull(files, "没有获取到路径下的内容");
        Assert.noNullElements(files, "路径下的内容为空");

        List<FileInfo> results = new ArrayList<>();
        int dicCount = 0;
        int fileCount = 0;
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getName());
            fileInfo.setUpdateTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
            fileInfo.setCanRead(file.canRead());
            fileInfo.setCanWrite(file.canWrite());
            String fileSize;
            if (file.isFile()) {
                fileSize = String.format("%.2fMB", FileUtils.sizeOf(file) / 1024.0 / 1024.0);
                fileInfo.setFileType(MimeEnum.getExplainByMimeType(Files.probeContentType(file.toPath())));
                fileCount++;
            } else {
                fileSize = String.format("%.2fMB", FileUtils.sizeOfDirectory(file) / 1024.0 / 1024.0);
                fileInfo.setFileType("文件夹");
                dicCount++;
            }
            fileInfo.setFileSize(fileSize);
            fileInfo.setOldFileName(file.getName());
            results.add(fileInfo);
        }

        results.sort(Comparator.comparing(FileInfo::getUpdateTime).reversed());

        return CommonResponse.success(String.format("路径下有【%s】个文件夹【%s】个文件", dicCount, fileCount), results);
    }

    @PostMapping("/v1/createMove")
    public CommonResponse<List<FileInfo>> createMove(@RequestBody CommonRequest CommonRequest) throws InterruptedException, IOException {
        String createMovePath = CommonRequest.getCreateMovePath();
        Assert.hasText(createMovePath, "请输入路径");

        File dic = new File(createMovePath);
        Assert.isTrue(dic.exists(), "不存在");
        Assert.isTrue(dic.isDirectory(), "不是文件夹");
        Assert.isTrue(dic.canRead(), "没有读权限");
        Assert.isTrue(dic.canWrite(), "没有写权限");

        File[] files = dic.listFiles();
        Assert.notNull(files, "没有获取到路径下的内容");
        Assert.noNullElements(files, "路径下的内容为空");

        int dicCount = 0;
        int moveCount = 0;
        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                File newDic = new File(dic, name.trim());
                FileUtils.moveFileToDirectory(file, newDic, true);
                moveCount++;
            } else {
                dicCount++;
            }
        }

        List<FileInfo> results = new ArrayList<>();
        files = dic.listFiles();
        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getName());
            fileInfo.setUpdateTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
            fileInfo.setCanRead(file.canRead());
            fileInfo.setCanWrite(file.canWrite());
            String fileSize;
            if (file.isFile()) {
                fileSize = String.format("%.2fMB", FileUtils.sizeOf(file) / 1024.0 / 1024.0);
                fileInfo.setFileType(MimeEnum.getExplainByMimeType(Files.probeContentType(file.toPath())));
            } else {
                fileSize = String.format("%.2fMB", FileUtils.sizeOfDirectory(file) / 1024.0 / 1024.0);
                fileInfo.setFileType("文件夹");
            }
            fileInfo.setFileSize(fileSize);
            fileInfo.setOldFileName(file.getName());
            results.add(fileInfo);
        }

        results.sort(Comparator.comparing(FileInfo::getFileSize).reversed());

        return CommonResponse.success(String.format("路径下有【%s】个文件夹【%s】个文件，对【%s】个文件进行了创建文件夹并移动文件至此文件夹！", dicCount, moveCount, moveCount), results);
    }

    public static void main(String[] args) throws IOException {
        File ff = new File("                       .txt");
        System.out.println(ff.exists());
        System.out.println(ff.getName());

        String str = "1.1.1.1";
        System.out.println(str.replace(".", " "));
        System.out.println(str.replaceAll(".", " "));
        System.out.println(str);

        File f = new File("D:\\test\\1111");
        String parent = f.getParent();
        File haoLe = new File(parent, "hao le");
        boolean b = f.renameTo(haoLe);
        System.out.println(b);

        String sha256Hex = DigestUtils.sha256Hex(new FileInputStream("D:\\test\\1111hao le\\1.txt"));
        System.out.println(sha256Hex);

        String dic = "D:\\test\\1111hao le";
        File file = new File(dic);
        System.out.println(file.getName());
        System.out.println(file.getParent());
        System.out.println(file.getPath());
        System.out.println(file.getParentFile().getName());
        boolean b1 = file.renameTo(new File(file.getParent(), "666"));
        System.out.println(b1);
    }

    private void processFiles(File directory, List<FileInfo> list) throws IOException {
        File[] files = directory.listFiles();
        Assert.notNull(files, "没有获取到路径下的内容");
        Assert.noNullElements(files, "路径下的内容为空");

        for (File file : files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setOldFileName(file.getName());
            fileInfo.setFileName(file.getName());
            fileInfo.setFilePath(file.getAbsolutePath());
            fileInfo.setUpdateTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
            fileInfo.setCanRead(file.canRead());
            fileInfo.setCanWrite(file.canWrite());
            if (file.isDirectory()) {
                fileInfo.setFileType("文件夹");
                fileInfo.setFileSize(String.format("%.2fMB", FileUtils.sizeOfDirectory(file) / 1024.0 / 1024.0));
                fileInfo.setCanRead(file.canRead());
                fileInfo.setCanWrite(file.canWrite());

                list.add(fileInfo);

                processFiles(file, list);
            } else {
                fileInfo.setFileSize(String.format("%.2fMB", FileUtils.sizeOf(file) / 1024.0 / 1024.0));
                fileInfo.setFileType(MimeEnum.getExplainByMimeType(Files.probeContentType(file.toPath())));
                fileInfo.setSha256(DigestUtils.sha256Hex(Files.readAllBytes(file.toPath())));

                list.add(fileInfo);
            }
        }
    }

}
