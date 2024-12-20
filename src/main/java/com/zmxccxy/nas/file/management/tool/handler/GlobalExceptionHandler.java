package com.zmxccxy.nas.file.management.tool.handler;

import com.zmxccxy.nas.file.management.tool.pojo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(Exception ex) {
        log.error("全局异常处理器：{}", ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(CommonResponse.error(500, "失败：" + ex.getMessage()), HttpStatus.OK);
    }

}
