package com.chuxing.bdp.controller;

import com.chuxing.bdp.model.rpc.common.Result;
import com.chuxing.bdp.service.FileService;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @date 2023/4/23 17:02
 * @author huangchenguang
 * @desc 文件管理
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * @date 2023/5/17 10:59
     * @desc 支持文件类型
     */
    private static final List<String> FILE_TYPE = Lists.newArrayList(".csv");

    /**
     * @date 2023/4/23 17:49
     * @author huangchenguang
     * @desc 上传文件
     */
    @RequestMapping("/uploadTable")
    public Result<Boolean> uploadTable(@RequestParam("file") MultipartFile file) {
        FILE_TYPE.stream().filter(Objects.requireNonNull(file.getOriginalFilename())::endsWith).findFirst().orElseThrow(() -> new RuntimeException("暂时不支持该文件类型"));
        fileService.uploadTable(file);
        return Result.success(true);
    }

}
