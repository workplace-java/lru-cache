package com.lru.lru_cache.controller;

import com.lru.lru_cache.constants.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/cache")
public class CacheController {

    // Small capacity to make eviction easy to see
    private final int CAPACITY = 3;

    private final Map<String, String> cache = Collections.synchronizedMap(
        new LinkedHashMap<>(3, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > CAPACITY;
            }
        }
    );

    @GetMapping("/add")
    public Map<String, String> add(@RequestParam String key, @RequestParam String val) {
        cache.put(key, val);
        return cache;
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return "Value: " + cache.getOrDefault(key, Constants.KEY_NOT_FOUND) + " | Current Cache: " + cache;
    }

    @GetMapping("/view")
    public Map<String, String> view() {
        return new LinkedHashMap<>(cache);
    }
}
