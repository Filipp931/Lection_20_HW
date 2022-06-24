package com.sber.caching_downloader.controller;

import com.sber.caching_downloader.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URL;

@Controller
public class MainController {
    Cache cache;

    public MainController(Cache cache) {
        this.cache = cache;
    }

    @GetMapping("/")
    public String main(){
        return "main.html";
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Object> download(@RequestParam URL url){
        return cache.get(url);
    }
}
