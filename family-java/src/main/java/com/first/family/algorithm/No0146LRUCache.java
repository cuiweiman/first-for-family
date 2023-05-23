package com.first.family.algorithm;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 146. LRU 缓存
 *
 * @description: https://leetcode.cn/problems/lru-cache/description/
 * @author: cuiweiman
 * @date: 2022/11/18 15:26
 */
public class No0146LRUCache {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(2, 1);
        cache.put(2, 2);
        System.out.println(cache.get(2));
        cache.put(1, 1);
        cache.put(4, 1);
        System.out.println(cache.get(2));
    }

}

class LRUCache {
    private int capacity;
    /**
     * 队列 记录 key 顺序，从而实现 LRU 最近最久未使用:
     * 1. 新缓存的元素、使用过的元素，放到队首。
     * 2. 容量溢出时，移除队尾元素。
     */
    private LinkedList<Integer> keyList;
    private HashMap<Integer, Integer> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.keyList = new LinkedList<>();
        this.map = new HashMap<>(capacity);
    }

    public int get(int key) {
        Integer element = key;
        // remove(int) 按照下标删除; remove(Integer)==remove(Object) 按照元素删除
        boolean remove = keyList.remove(element);
        if (remove) {
            keyList.addFirst(element);
            return map.get(key);
        }
        return -1;
    }

    public void put(int key, int value) {
        Integer element = key;
        boolean remove = keyList.remove(element);
        if (remove) {
            keyList.addFirst(element);
            map.put(element, value);
            return;
        }
        if (map.size() >= capacity) {
            // 容量溢出，移除队尾元素
            Integer removeKey = keyList.removeLast();
            map.remove(removeKey);
        }
        keyList.addFirst(element);
        map.put(key, value);
    }
}