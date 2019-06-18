package com.liaozl.demo.disconf;

import com.liaozl.demo.disconf.properties.AppConfig;
import com.liaozl.demo.disconf.properties.AppConfig2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisconfDemoApplicationTests {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AppConfig2 appConfig2;

    @Test
    public void contextLoads() {
        int i = 0;
        while (true) {
            try {
                if (i > 10000) {
                    break;
                }

                log.info("appConfig={}", appConfig.toString());

                log.info("appConfig2={}", appConfig2.toString());

                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("contextLoads error:", e);
                break;
            }
            i++;
        }
    }

}
