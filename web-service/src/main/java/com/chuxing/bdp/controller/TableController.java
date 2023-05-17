package com.chuxing.bdp.controller;

import com.chuxing.bdp.model.rpc.common.Result;
import com.chuxing.bdp.model.rpc.request.TableListQueryRequest;
import com.chuxing.bdp.service.TableService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @date 2023/5/17 10:54
 * @author huangchenguang
 * @desc 库表管理
 */
@RestController
@RequestMapping("/table")
public class TableController {

    @Resource
    private TableService tableService;

    /**
     * @date 2023/5/17 10:53
     * @author huangchenguang
     * @desc 查询表列表
     */
    @RequestMapping("/searchTables")
    public Result<List<String>> searchTables(@RequestBody @Valid TableListQueryRequest tableListQueryRequest) {
        return Result.success(tableService.searchTables(tableListQueryRequest.getTableName()));
    }

}
