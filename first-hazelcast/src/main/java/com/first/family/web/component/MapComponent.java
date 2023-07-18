package com.first.family.web.component;

import com.hazelcast.map.IMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: <a url="https://www.jianshu.com/p/a015ffb2dd8f"/>
 * @author: cuiweiman
 * @date: 2023/7/12 13:45
 * @see com.hazelcast.map.IMap
 * @see java.util.HashMap
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MapComponent {

    private final IMap<String, String> iMap;

    /**
     * @param key   key
     * @param value val
     * @return old value when key already exists, or "null"
     */
    public String put(String key, String value) {
        return String.valueOf(iMap.put(key, value));
    }

    public String get(String key) {
        return iMap.get(key);
    }

    public boolean containsKey(String key) {
        return iMap.containsKey(key);
    }

    /**
     * 先判断指定的键（key）是否存在, 不存在则将键/值对插入到 HashMap 中
     *
     * @param key   key
     * @param value value
     * @return 若key存在, 返回 旧的value值；若key不存在, 则返回 新的 value 值
     */
    public String putIfAbsent(String key, String value) {
        return String.valueOf(iMap.putIfAbsent(key, value));
    }

    /**
     * 普通更新, key必须已存在, 否则报错:
     * <pre class="code">
     *  java.lang.NullPointerException: Null value is not allowed!
     * </pre>
     *
     * @param key    key
     * @param newVal new value
     * @return 若key存在, 返回 旧的value值；若key不存在, 则返回新的 value 值
     */
    public String replace(String key, String newVal) {
        if (!iMap.containsKey(key)) {
            return "key " + key + " 不存在, 无法更新";
        }
        return iMap.replace(key, newVal);
    }

    /**
     * CAS 更新, key必须已存在,否则报错:
     * <pre class="code">
     *  java.lang.NullPointerException: Null value is not allowed!
     * </pre>
     *
     * @param key       key
     * @param exceptVal 期望值/当前值
     * @param newVal    新值
     * @return CAS更新结果
     */
    public boolean replace(String key, String exceptVal, String newVal) {
        return iMap.replace(key, exceptVal, newVal);
    }

}
