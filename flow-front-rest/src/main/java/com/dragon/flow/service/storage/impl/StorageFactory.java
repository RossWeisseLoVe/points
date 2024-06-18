package com.dragon.flow.service.storage.impl;

import cn.hutool.core.io.FileUtil;
import com.dragon.flow.properties.StorageProperties;
import com.dragon.flow.service.storage.Storage;
import com.dragon.flow.vo.storage.FileStorage;
import com.dragon.tools.common.FileUtils;
import com.dragon.tools.common.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 提供存储服务类，所有存储服务均由该类对外提供
 */
@Service
public class StorageFactory {
    @Autowired
    private StorageProperties storageProperties;
    private Storage storage;

    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param fileName      文件索引名
     */
    public FileStorage store(InputStream inputStream, long contentLength, String contentType, String filePath, String fileName) throws Exception {
        String active = storageProperties.getActive();
        String key = generateKey(fileName);
        switch (active) {
            case "local":
                LocalStorage localStorage = new LocalStorage();
                StorageProperties.Local local = this.storageProperties.getLocal();
                localStorage.setAddress(local.getAddress());
                localStorage.setStoragePath(local.getStoragePath());
                storage = localStorage;
                if(StringUtils.isBlank(filePath)){
                    filePath = FileUtils.getDateFmtFilePath();
                }

                key = filePath + "/" + key;
                break;
            default:
                throw new Exception("当前存储模式 " + active + " 不支持");
        }
        contentType = StorageFactory.getcontentType(fileName);
        storage.store(inputStream, contentLength, contentType, filePath, key);
        String url = generateUrl(key);
        FileStorage storageInfo = new FileStorage();
        storageInfo.setName(fileName);
        storageInfo.setSize((int) contentLength);
        storageInfo.setType(contentType);
        storageInfo.setKey(key);
        storageInfo.setUrl(url);
        return storageInfo;
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }


    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);
        String key = UUIDGenerator.generate() + suffix;
        return key;
    }

    public Stream<Path> loadAll() {
        return storage.loadAll();
    }

    public Path load(String keyName) {
        return storage.load(keyName);
    }

    public Resource loadAsResource(String keyName) {
        return storage.loadAsResource(keyName);
    }

    public void delete(String keyName) {
        storage.delete(keyName);
    }

    private String generateUrl(String keyName) {
        return storage.generateUrl(keyName);
    }
}
