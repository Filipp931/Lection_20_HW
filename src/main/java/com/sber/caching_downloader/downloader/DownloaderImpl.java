package com.sber.caching_downloader.downloader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DownloaderImpl implements Downloader{
    @Value("${downloadPath}")
    Path downloadDirectoryPath;

    @Override
    @Async
    public Path download(URL url) {
        Path filePath = getFileName(url);
        URLConnection connection;
        try {
            connection = url.openConnection();
            InputStream is = connection.getInputStream();
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
            is.close();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostConstruct
    private void checkDownloadDirectory(){
        if( !Files.exists(downloadDirectoryPath) ) {
            try {
                Files.createDirectory(downloadDirectoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Path getFileName(URL url) {
        String fileName = url.toString().substring(url.toString().lastIndexOf('/')+1);
        return Paths.get(downloadDirectoryPath.toString()+"/"+fileName);
    }

}
