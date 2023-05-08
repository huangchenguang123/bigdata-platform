package com.chuxing.bdp.service;

import com.chuxing.bdp.config.AppConfig;
import com.chuxing.bdp.service.execute.StandaloneExecute;
import com.chuxing.bdp.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @date 2023/4/23 17:55
 * @author huangchenguang
 * @desc csv service
 */
@Slf4j
@Service
public class CsvService {

    @Resource
    private AppConfig appConfig;

    @Resource
    private StandaloneExecute standaloneExecute;

    /**
     * @date 2023/4/24 15:20
     * @author huangchenguang
     * @desc upload file to local datawarehouse
     */
    public void uploadTable(MultipartFile data, String tableName) {
        try {
            // save file to tmp path
            String filePath = String.format("%s%s/%s", appConfig.getDatawarehousePath(), appConfig.getTmpPath(), data.getOriginalFilename());
            FileUtils.saveFile(data, filePath);
            // upload table to db
            String dropSql = String.format("drop table if exists %s", tableName);
            standaloneExecute.executeDml(dropSql);
            String createSql = String.format("create table if not exists %s as select * from read_csv_auto('%s');", tableName, filePath);
            standaloneExecute.executeDml(createSql);
            // delete file
            FileUtils.deleteFile(filePath);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("upload table fail, table={}", tableName, e);
            throw new RuntimeException("upload table fail");
        }
    }

}
