package com.zmxccxy.nas.file.management.tool.pojo;

import lombok.Data;

@Data
public class FileInfo {

    private String fileName;
    private String newFileName;
    private String oldFileName;
    private String filePath;
    private String fileType;
    private String fileSize;
    private String fileExt;
    private String sha256;
    private String updateTime;
    private Boolean canRead;
    private String canReadString;
    private Boolean canWrite;
    private String canWriteString;

    public String getCanReadString() {
        if (this.canRead == null) {
            return "-";
        }
        if (this.canRead) {
            return "可读";
        } else {
            return "不可读";
        }
    }

    public String getCanWriteString() {
        if (this.canWrite == null) {
            return "-";
        }
        if (this.canWrite) {
            return "可写";
        } else {
            return "不可写";
        }
    }

}
