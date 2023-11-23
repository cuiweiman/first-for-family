package com.first.family.controller;

import com.first.family.model.domain.SseEmitterUTF8;
import com.first.family.model.request.ChatReq;
import com.first.family.server.ChatSseServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/5 22:08
 */
@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SseController {

    private final ChatSseServer chatSseServer;

    @RequestMapping("/connect")
    public SseEmitterUTF8 connect(@RequestParam String clientId) {
        return chatSseServer.createConnect(clientId);
    }

    @RequestMapping("/close")
    public Boolean complete(@RequestParam String clientId) {
        return chatSseServer.complete(clientId);
    }


    @PostMapping("/chat")
    public void chat(@RequestBody ChatReq chatReq) {
        chatSseServer.sendMessage(chatReq);
    }


}
