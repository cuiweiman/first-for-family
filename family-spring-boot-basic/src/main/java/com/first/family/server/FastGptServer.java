package com.first.family.server;

import com.first.family.model.domain.SseEmitterUTF8;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/12/6 15:03
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FastGptServer {

    @Value("${chat.fast.chatUrl}")
    private String chatUrl;
    @Value("${chat.fast.token}")
    private String token;

    private final HttpClient httpClient;

    public void streamSearch() throws IOException, InterruptedException {
        String param = "{\"chatId\":\"andong-test-20231206145650\",\"stream\":true," +
                "\"detail\":false,\"variables\":{\"uid\":\"andong\",\"name\":\"安冬\"}," +
                "\"messages\":[{\"content\":\"中国有几个少数名族\",\"role\":\"user\"}]}";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(chatUrl))
                .setHeader(HttpHeaders.AUTHORIZATION, token)
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(param))
                .build();

        HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (HttpStatus.OK.value() == response.statusCode()) {
            try (InputStream body = response.body();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(body))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("Finish");
            }
        }
    }

    public void streamSearch(SseEmitterUTF8 sseEmitterUtf8) {
        String param = "{\"chatId\":\"andong-test-20231206145650\",\"stream\":true," +
                "\"detail\":false,\"variables\":{\"uid\":\"andong\",\"name\":\"安冬\"}," +
                "\"messages\":[{\"content\":\"中国有几个少数名族\",\"role\":\"user\"}]}";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(chatUrl))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(param))
                .build();

        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (HttpStatus.OK.value() == response.statusCode()) {
                try (InputStream body = response.body();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(body))) {
                    String line = bufferedReader.readLine();
                    while (Objects.nonNull(line)) {
                        line = line.replace("data:", "").trim();
                        if (StringUtils.hasText(line)) {
                            sseEmitterUtf8.send(line, MediaType.ALL);
                        }
                        System.out.println(line);
                        line = bufferedReader.readLine();
                    }
                    sseEmitterUtf8.completeWithError(new RuntimeException("hello sse"));
                    // sseEmitterUtf8.complete();
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream stream() throws IOException, InterruptedException {
        String param = "{\"chatId\":\"andong-test-20231206145650\",\"stream\":true," +
                "\"detail\":false,\"variables\":{\"uid\":\"andong\",\"name\":\"安冬\"}," +
                "\"messages\":[{\"content\":\"中国有几个少数名族\",\"role\":\"user\"}]}";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(chatUrl))
                .headers(HttpHeaders.AUTHORIZATION, token, HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(param))
                .build();

        HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (HttpStatus.OK.value() == response.statusCode()) {
            return response.body();
        }
        System.out.println("ERROR");
        return null;
    }


}
