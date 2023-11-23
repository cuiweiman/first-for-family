package com.first.family.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.first.family.model.request.ErnieBotReq;
import com.first.family.model.resp.ErnieChatResp;
import com.first.family.model.resp.ErnieTokenResp;
import com.first.family.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/8 13:53
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ErnieBotServer {

    @Value("${chat.ernieBot4.ak}")
    private String ak;

    @Value("${chat.ernieBot4.sk}")
    private String sk;

    @Value("${chat.ernieBot4.tokenUrl}")
    private String tokenUrl;

    @Value("${chat.ernieBot4.chatUrl}")
    private String chatUrl;

    @Value("${chat.ernieBot4.tokenValid}")
    private Integer tokenValid;

    private final RedissonClient redissonClient;
    private final HttpClient httpClient;

    private static final String ACCESS_TOKEN_KEY = "search-server:ernie-bot:access-token";

    public String getToken() {
        String token = (String) redissonClient.getBucket(ACCESS_TOKEN_KEY).get();
        if (StringUtils.hasText(token)) {
            return token;
        }
        try {
            String format = String.format(tokenUrl, ak, sk);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(format)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (HttpStatus.OK.value() == response.statusCode()) {
                String body = response.body();
                try {
                    ErnieTokenResp ernieTokenResp = JsonUtil.parseObject(body, ErnieTokenResp.class);
                    String accessToken = ernieTokenResp.getAccessToken();
                    redissonClient.getBucket(ACCESS_TOKEN_KEY).set(accessToken, Duration.ofDays(tokenValid));
                    return accessToken;
                } catch (JsonProcessingException e) {
                    log.error(body);
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error("HttpClient get token error, {}", e.getMessage());
        }
        return null;
    }

    public String chat(List<ErnieBotReq> messages) {
        try {
            String token = getToken();
            if (!StringUtils.hasText(token)) {
                TimeUnit.MILLISECONDS.sleep(200);
                token = getToken();
                if (!StringUtils.hasText(token)) {
                    log.error("token 获取失败");
                    return "系统忙，请联系管理员";
                }
            }
            String format = String.format(chatUrl, token);
            Map<String, Object> map = new HashMap<>(2);
            map.put("messages", messages);
            String param = JsonUtil.toJson(map);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(format))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(param)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (HttpStatus.OK.value() == response.statusCode()) {
                String body = response.body();
                try {
                    ErnieChatResp ernieTokenResp = JsonUtil.parseObject(body, ErnieChatResp.class);
                    return ernieTokenResp.getResult();
                } catch (JsonProcessingException e) {
                    log.error(body);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("HttpClient chat param invalid: {}, errMsg: {}", messages, e.getMessage());
        } catch (IOException | InterruptedException e) {
            log.error("HttpClient chat param invalid: {}", e.getMessage());
        }
        return "系统忙，请联系管理员";
    }

}
