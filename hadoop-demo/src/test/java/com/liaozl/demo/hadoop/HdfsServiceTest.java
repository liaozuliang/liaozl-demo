package com.liaozl.demo.hadoop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/14 11:56
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HdfsServiceTest {

    @Autowired
    private HdfsService hdfsService;

    @Test
    public void testMkdirs() {
        boolean ret = hdfsService.mkdirs("/input/liaozl");
        log.info("========testMkdir========={}", ret);
    }

    @Test
    public void testDelete() {
        boolean ret = hdfsService.delete("/input");
        log.info("========testDelete========={}", ret);
    }

    @Test
    public void testListFile() {
        List<String> filePathList = hdfsService.listFile("/");
        filePathList.forEach(path -> {
            log.info("filePath: {}", path);
        });

    }

    @Test
    public void testUpload() {
        boolean ret = hdfsService.upload("C:/Users/1/Desktop/log.txt", "/input/liaozl/log.txt");
        log.info("========testUpload========={}", ret);
    }

    @Test
    public void testDownload() {
        boolean ret = hdfsService.download("/input/liaozl/log.txt", "C:/Users/1/Desktop/log2.txt");
        log.info("========testDownload========={}", ret);
    }
}
