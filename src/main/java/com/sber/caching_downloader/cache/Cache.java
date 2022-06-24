package com.sber.caching_downloader.cache;

import org.springframework.http.ResponseEntity;

public interface Cache<T> {
    ResponseEntity get(T t);
    void put(T t);
}
