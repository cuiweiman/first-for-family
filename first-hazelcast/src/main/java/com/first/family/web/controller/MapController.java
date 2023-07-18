package com.first.family.web.controller;

import com.first.family.web.component.MapComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: map测试
 * @author: cuiweiman
 * @date: 2023/7/12 09:59
 */
@RestController
@RequestMapping("/imap")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MapController {

    private final MapComponent mapComponent;

    @GetMapping("/put")
    public String put(String key, String value) {
        String oldVal = mapComponent.put(key, value);
        return "旧值: ".concat(String.valueOf(oldVal));
    }

    @GetMapping("/get")
    public String get(String key) {
        boolean containsKey = mapComponent.containsKey(key);
        if (containsKey) {
            return mapComponent.get(key);
        }
        return "null";
    }

    @GetMapping("/putIfAbsent")
    public String putIfAbsent(String key, String value) {
        return mapComponent.putIfAbsent(key, value);
    }

    @GetMapping("/replace")
    public String replace(String key, String newVal) {
        return mapComponent.replace(key, newVal);
    }

    @GetMapping("/casReplace")
    public boolean replace(String key, String oldVal, String newVal) {
        return mapComponent.replace(key, oldVal, newVal);
    }

}
