package com.chuxing.bdp.controller;

import com.chuxing.bdp.model.rpc.common.Result;
import com.chuxing.bdp.service.CsvService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @date 2023/4/23 17:02
 * @author huangchenguang
 * @desc csv controller
 */
@RestController
@RequestMapping("/csv")
public class CsvController {

    @Resource
    private CsvService csvService;

    /**
     * @date 2023/4/23 17:49
     * @author huangchenguang
     * @desc uploadTable
     */
    @RequestMapping("/uploadTable")
    public Result<Boolean> uploadTable(@RequestParam("data") MultipartFile data) {
        // only supported csv
        if (!Objects.requireNonNull(data.getOriginalFilename()).endsWith(".csv")) {
            throw new RuntimeException("only csv file is supported");
        }
        csvService.uploadTable(data);
        return Result.success(true);
    }

}
