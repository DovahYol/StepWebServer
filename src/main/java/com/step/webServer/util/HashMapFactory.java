package com.step.webServer.util;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class HashMapFactory<K, V> implements MapFactory<K, V>{
    @Override
    public Map<K, V> create() {
        return new HashMap<>();
    }
}
