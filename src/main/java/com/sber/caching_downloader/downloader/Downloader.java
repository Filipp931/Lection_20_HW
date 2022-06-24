package com.sber.caching_downloader.downloader;

import java.net.URL;
import java.nio.file.Path;

public interface Downloader {
    Path download(URL url);
}
