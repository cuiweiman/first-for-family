package com.first.family.web.controller;

import com.hazelcast.cache.ICache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.Cache;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * <a url="https://hazelcast.com/clients/java/"></a>
 *
 * @description: iCache
 * @author: cuiweiman
 * @date: 2023/7/12 09:59
 */
@RestController
@RequestMapping("/jcache")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CacheController {

    private final Cache<String, String> jCache;

    @GetMapping("/put")
    public String put(String key, String value) {
        jCache.put(key, value);
        return "success";
    }

    @GetMapping("/get")
    public String get(String key) {
        return jCache.get(key);
    }

    @GetMapping("/getAsync")
    public String getAsync(String key) {
        try {
            ICache iCache = jCache.unwrap(ICache.class);
            Object o = iCache.getAsync(key).toCompletableFuture().get();
            return String.valueOf(o);
        } catch (ExecutionException | InterruptedException e) {
            return e.getMessage();
        }
    }


    @GetMapping("/putAsync")
    public String putAsync(String key, String value) {
        try {
            ICache iCache = jCache.unwrap(ICache.class);
            CompletionStage completionStage = iCache.putAsync(key, value);
            Object o = completionStage.toCompletableFuture().get();
            return String.valueOf(o);
        } catch (ExecutionException | InterruptedException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/size")
    public int size() {
        ICache iCache = jCache.unwrap(ICache.class);
        return iCache.size();
    }

}
