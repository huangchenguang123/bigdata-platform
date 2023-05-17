package com.chuxing.bdp.model.rpc.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2023/5/17 11:08
 * @author huangchenguang
 * @desc 表列表查询请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableListQueryRequest {

    /**
     * @date 2023/5/17 11:08
     * @desc 表名
     */
    private String tableName;

}
