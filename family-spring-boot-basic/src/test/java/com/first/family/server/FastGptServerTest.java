package com.first.family.server;


import com.first.family.SpringBootBasic;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = SpringBootBasic.class)
class FastGptServerTest {

    @Resource
    private FastGptServer fastGptServer;

    @Test
    void streamSearch() {
        try {
            fastGptServer.streamSearch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}