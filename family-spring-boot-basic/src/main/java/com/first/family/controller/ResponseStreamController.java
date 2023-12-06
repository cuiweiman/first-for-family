package com.first.family.controller;

import com.first.family.server.FastGptServer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @description: 流式响应
 * @author: cuiweiman
 * @date: 2023/12/6 14:50
 */
@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ResponseStreamController {

    private final FastGptServer fastGptServer;

    @GetMapping(value = "/resp", name = "流式响应")
    public void resp(HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (InputStream inputStream = fastGptServer.stream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
