package com.chuxing.bdp.model.rpc.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @date 2023/4/26 09:58
 * @author huangchenguang
 * @desc 自然语言查询请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdHotSmartQueryRequest {

    /**
     * @date 2023/4/26 09:59
     * @desc query
     */
    @NotBlank
    private String query;

    /**
     * @date 2023/5/5 17:19
     * @desc tables
     */
    @NotNull
    private List<String> tables;

}
