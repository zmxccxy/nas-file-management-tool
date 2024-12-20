package com.zmxccxy.nas.file.management.tool.pojo;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private Integer code;
    private String message;
    private T data;

    // 成功响应
    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setMessage("操作成功");
        commonResponse.setData(data);
        return commonResponse;
    }

    // 成功响应
    public static <T> CommonResponse<T> success(String message, T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setMessage(message);
        commonResponse.setData(data);
        return commonResponse;
    }

    // 失败响应
    public static <T> CommonResponse<T> error(Integer code, String message) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(code);
        commonResponse.setMessage(message);
        commonResponse.setData(null);
        return commonResponse;
    }
}
