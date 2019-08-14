package com.liaozl.demo.hadoop;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/14 11:48
 */
@Slf4j
@Service
public class HdfsService {

    @Autowired
    private FileSystem fileSystem;

    @PreDestroy
    public void closeFileSystem() {
        try {
            fileSystem.close();
        } catch (Exception e) {
            log.error("closeFileSystem error: ", e);
        }
    }

    public List<String> listFile(String path) {
        List<String> filePathList = new ArrayList<>();

        try {
            RemoteIterator<LocatedFileStatus> remoteIterator = fileSystem.listFiles(new Path(path), true);
            while (remoteIterator.hasNext()) {
                LocatedFileStatus lfs = remoteIterator.next();
                filePathList.add(lfs.getPath().toString());
            }
        } catch (Exception e) {
            log.error("listFile error: ", e);
        }

        return filePathList;
    }

    public boolean mkdirs(String path) {
        try {
            return fileSystem.mkdirs(new Path(path));
        } catch (Exception e) {
            log.error("mkdirs error: ", e);
        }
        return false;
    }

    public boolean delete(String path) {
        try {
            return fileSystem.delete(new Path(path), true); // 递归删除
        } catch (Exception e) {
            log.error("delete error: ", e);
        }
        return false;
    }

    public boolean download(String sourcePath, String savePath) {
        try {
            fileSystem.copyToLocalFile(new Path(sourcePath), new Path(savePath));
            return true;
        } catch (Exception e) {
            log.error("download error: ", e);
        }
        return false;
    }

    public boolean upload(String sourcePath, String savePath) {
        try {
            fileSystem.copyFromLocalFile(new Path(sourcePath), new Path(savePath));
            return true;
        } catch (Exception e) {
            log.error("upload error: ", e);
        }
        return false;
    }

}
