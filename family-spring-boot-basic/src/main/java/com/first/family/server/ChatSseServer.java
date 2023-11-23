package com.first.family.server;

import com.first.family.model.domain.SseEmitterUTF8;
import com.first.family.model.request.ChatReq;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * <a href="https://redisson.org/articles/redis-streams-for-java.html">Redisson-Stream实现消息队列</a>
 * <a href="https://www.coder.work/article/1399790">SSE 集群设计</a>
 * <a href="https://stackoverflow.com/questions/38837103/server-sent-event-sse-cluster-connection-handling">SSE 集群设计</a>
 *
 * @description: 文心一言 sse 服务
 * @author: cuiweiman
 * @date: 2023/11/5 22:34
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChatSseServer implements InitializingBean {

    private final RTopic remoteCompleteQueue;
    private final RTopic remoteSendQueue;
    private final ErnieBotServer ernieBotServer;

    private static final Cache<String, SseEmitterUTF8> SSE_CACHE =
            Caffeine.newBuilder().maximumSize(100_000).expireAfterAccess(1, TimeUnit.HOURS)
                    .removalListener((clientId, sseEmitterUTF8, cause) -> {
                        log.debug("ClientID {}, sseEmitterUTF8 {} was removed, cause {}", clientId, sseEmitterUTF8, cause);
                    }).build(new CacheLoader<>() {
                        @Override
                        public @Nullable SseEmitterUTF8 load(String key) {
                            return null;
                        }
                    });


    @Override
    public void afterPropertiesSet() {
        completeMessageListener();
        sendMessageListener();
    }

    private void completeMessageListener() {
        remoteCompleteQueue.addListener(String.class, (channel, msg) -> {
            SseEmitterUTF8 sseEmitterUtf8 = SSE_CACHE.getIfPresent(msg);
            if (Objects.nonNull(sseEmitterUtf8)) {
                sseEmitterUtf8.complete();
                removeClientId(msg);
            }
        });
    }

    private void sendMessageListener() {
        remoteSendQueue.addListener(ChatReq.class, (channel, chatReq) -> {
            SseEmitterUTF8 sseEmitterUtf8 = SSE_CACHE.getIfPresent(chatReq.getClientId());
            if (Objects.nonNull(sseEmitterUtf8)) {
                try {
                    String chat = ernieBotServer.chat(chatReq.getMessages());
                    sseEmitterUtf8.send(chat, MediaType.ALL);
                } catch (IOException e) {
                    log.error("[ChatSseServer#sendMessage] " +
                            "error clientId: {}, errMsg: {}", chatReq.getClientId(), e.getMessage());
                }
            } else {
                log.debug("[ChatSseServer#sendMessage] SseEmitter not exists on this node, clientId: {}",
                        chatReq.getClientId());
            }
        });
    }

    public SseEmitterUTF8 createConnect(String clientId) {
        SseEmitterUTF8 sseEmitter = new SseEmitterUTF8(0L);
        sseEmitter.onCompletion(completionCallBack(clientId));
        sseEmitter.onTimeout(timeOutCallBack(clientId));
        sseEmitter.onError(errorCallBack(clientId));

        SSE_CACHE.put(clientId, sseEmitter);
        log.debug("[SSE connect] success, currClientId: {}", clientId);
        return sseEmitter;
    }

    public Boolean complete(String clientId) {
        remoteCompleteQueue.publish(clientId);
        return true;
    }

    public void sendMessage(ChatReq chatReq) {
        remoteSendQueue.publish(chatReq);
    }


    /**
     * SSE连接断开时的回调函数
     *
     * @param clientId clientId
     * @return Runnable Function
     */
    private Runnable completionCallBack(String clientId) {
        return () -> {
            log.debug("[SSE connect] completion, timeout or network error, clientId = {}", clientId);
            removeClientId(clientId);
        };
    }

    private Runnable timeOutCallBack(String clientId) {
        return () -> {
            log.debug("[SSE connect] timeout clientId={}", clientId);
            removeClientId(clientId);
        };
    }

    private Consumer<Throwable> errorCallBack(String clientId) {
        return throwable -> {
            log.error("[SSE connect] error, clientId = {}", clientId, throwable);
            removeClientId(clientId);
        };
    }

    private void removeClientId(String clientId) {
        SseEmitterUTF8 sseEmitterUtf8 = SSE_CACHE.getIfPresent(clientId);
        if (Objects.nonNull(sseEmitterUtf8)) {
            SSE_CACHE.invalidate(clientId);
            log.debug("[SSE remove client connector] clientId = {}", clientId);
        }
    }


}
