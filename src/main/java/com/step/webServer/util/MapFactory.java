package com.step.webServer.util;

import java.util.Map;

public interface MapFactory<K, V> {
    Map<K, V> create();
}
