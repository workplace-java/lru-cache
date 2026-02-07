package com.lru.lru_cache.controller;

import com.lru.lru_cache.constants.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequestMapping("reentrantLockCache")
public class ReentrantLockCacheController {

    private final int CAPACITY = 3;

    /**
     * Define an internal map which manages eviction of oldest or least recently used element
     * Cache is defined with capacity 3 with load factor and access order.
     */
    private final Map<String, String> cache = new LinkedHashMap<>(CAPACITY, 0.75f, false) {

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > CAPACITY;
        }
    };

    // Initialize the ReadWriteLock
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    @GetMapping("/add")
    public Map<String, String> add(@RequestParam String key, @RequestParam String val) {
        writeLock.lock();
        try {
            cache.put(key, val);
            return new LinkedHashMap<>(cache);
        } finally {
            writeLock.unlock();
        }
    }

    @GetMapping("get")
    public String get(@RequestParam String key) {
        writeLock.lock();
        try {
            return cache.getOrDefault(key, Constants.KEY_NOT_FOUND);
        } finally {
            writeLock.unlock();
        }
    }

    @GetMapping("view")
    public Map<String, String> view() {
        readLock.lock();
        try {
            return new LinkedHashMap<>(cache);
        } finally {
            readLock.unlock();
        }
    }
}
