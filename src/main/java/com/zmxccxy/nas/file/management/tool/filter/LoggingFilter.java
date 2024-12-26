package com.zmxccxy.nas.file.management.tool.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();

        // 请求信息
        String uri = httpRequest.getRequestURI();
        String clientIp = httpRequest.getRemoteAddr();
        String method = httpRequest.getMethod();
        String queryString = httpRequest.getQueryString();
        String requestParams = queryString != null ? "?" + queryString : "";

        log.info("Request received: IP={} Method={} URI={} Params={}", clientIp, method, uri, requestParams);

        try {
            // 执行过滤器链
            chain.doFilter(request, response);

            // 响应信息
            int status = httpResponse.getStatus();
            long duration = System.currentTimeMillis() - startTime;

            log.info("Response sent: IP={} Method={} URI={} Status={} Duration={}ms", clientIp, method, uri, status, duration);

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("Error processing request: IP={} Method={} URI={} Duration={}ms Error={}", clientIp, method, uri, duration, e.getMessage(), e);
        }
    }
}
