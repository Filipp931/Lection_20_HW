package com.sber.caching_downloader;

import com.sber.caching_downloader.bankomat.server.Server;
import com.sber.caching_downloader.bankomat.server.exceptions.AccountIsLockedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CachingDownloaderApplicationTests {
    Server server;

    @Autowired
    public CachingDownloaderApplicationTests(Server server) {
        this.server = server;
    }

    @Test
    void contextLoads() {
        for (int i = 0; i < 6; i++) {
            try {
                server.checkPin(1321);
            } catch (AccountIsLockedException e) {
                e.printStackTrace();
            }
        }
    }

}
