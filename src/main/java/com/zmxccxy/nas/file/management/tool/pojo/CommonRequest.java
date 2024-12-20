package com.zmxccxy.nas.file.management.tool.pojo;

import lombok.Data;

import java.util.List;

@Data
public class CommonRequest {

    // 替换
    private String path;
    private String from;
    private String to;
    private List<String> replaceFileTypes;

    // 新建&移动
    private String createMovePath;

    // 删除
    private String deleteQueryPath;
    private String deletePath;

    // 去重
    private String duplicateQueryPath;


}
