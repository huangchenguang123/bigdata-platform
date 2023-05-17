package com.chuxing.bdp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @date 2023/4/24 15:22
 * @author huangchenguang
 * @desc 文件工具类
 */
@Slf4j
public class FileUtils {

    /**
     * @date 2023/4/24 15:22
     * @author huangchenguang
     * @desc 创建目录
     */
    public static void mkdirs(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            try {
                boolean result = directory.mkdirs();
                if (!result) {
                    throw new RuntimeException("mkdirs failed");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @date 2023/4/25 19:40
     * @author huangchenguang
     * @desc 保存文件
     */
    public static void saveFile(MultipartFile sourceFile, String targetPath) {
        try {
            File file = new File(targetPath);
            sourceFile.transferTo(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @date 2023/4/25 19:40
     * @author huangchenguang
     * @desc 删除文件
     */
    public static void deleteFile(String targetPath) {
        try {
            File file = new File(targetPath);
            boolean result = file.delete();
            if (!result) {
                log.error("delete file fail");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
