package com.sber.caching_downloader.cache;

import com.sber.caching_downloader.downloader.Downloader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CacheImpl implements Cache<URL>{
    Downloader downloader;
    Map<URL, ResponseEntity<Object>> cache = new WeakHashMap<>();
    Lock lock = new ReentrantLock();
    public CacheImpl(Downloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public ResponseEntity<Object> get(URL url) {
        Map.Entry entry = getEntry(url);
        if(entry == null) {
            lock.lock();
            if(entry == null) {
                cache.put(url, null);
                entry = getEntry(url);
                synchronized (entry) {
                    lock.unlock();
                    Path filePath = downloader.download(url);
                    if(filePath != null) {
                        Resource file = new FileSystemResource(filePath);
                        MultiValueMap<String, String> headers = new HttpHeaders();
                        headers.set("Date", LocalDateTime.now().toString());
                        ResponseEntity responseEntity = new ResponseEntity(file, headers, HttpStatus.OK);
                        cache.put(url, responseEntity);
                    } else {
                        cache.remove(url);
                    }
                }
            }
        }
        return cache.get(url);
    }

    @Override
    public void put(URL url) {

    }

    private Map.Entry getEntry(URL url) {
        return cache.entrySet().stream().filter(urlResponseEntityEntry -> urlResponseEntityEntry.getKey().equals(url)).findAny().orElse(null);
    }
}
