package com.chuxing.bdp.controller;

import com.chuxing.bdp.model.rpc.common.Result;
import com.chuxing.bdp.service.SystemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @date 2023/4/23 17:02
 * @author huangchenguang
 * @desc 系统管理
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Resource
    private SystemService systemService;

    /**
     * @date 2023/4/23 17:12
     * @author huangchenguang
     * @desc 查询系统运行模式
     */
    @RequestMapping("/getMode")
    public Result<String> getMode() {
        return Result.success(systemService.getMode());
    }

}
