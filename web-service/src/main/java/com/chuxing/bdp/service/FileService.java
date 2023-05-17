package com.chuxing.bdp.service;

import com.chuxing.bdp.config.AppConfig;
import com.chuxing.bdp.service.execute.StandaloneExecute;
import com.chuxing.bdp.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @date 2023/4/23 17:55
 * @author huangchenguang
 * @desc 文件服务
 */
@Slf4j
@Service
public class FileService {

    @Resource
    private AppConfig appConfig;

    @Resource
    private StandaloneExecute standaloneExecute;

    /**
     * @date 2023/4/24 15:20
     * @author huangchenguang
     * @desc 上传文件
     */
    public void uploadTable(MultipartFile data) {
        String tableName = Objects.requireNonNull(data.getOriginalFilename()).replace(".csv", "");
        try {
            String filePath = String.format("%s%s/%s", appConfig.getDatawarehousePath(), appConfig.getTmpPath(), data.getOriginalFilename());
            FileUtils.saveFile(data, filePath);
            String dropSql = String.format("drop table if exists %s", tableName);
            standaloneExecute.executeDml(dropSql);
            String createSql = String.format("create table if not exists %s as select * from read_csv_auto('%s');", tableName, filePath);
            standaloneExecute.executeDml(createSql);
            FileUtils.deleteFile(filePath);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("上传文件失败, 数据集={}", tableName, e);
            throw new RuntimeException("上传文件失败");
        }
    }

}
