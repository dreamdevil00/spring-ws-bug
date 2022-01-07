package com.example.demo;

import com.example.demo.service.WebService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zero
 */
@SpringBootTest
public class WebServiceTest {
    @Autowired
    private WebService webService;

    @Test
    public void test() {
        webService.bar();
    }
}
