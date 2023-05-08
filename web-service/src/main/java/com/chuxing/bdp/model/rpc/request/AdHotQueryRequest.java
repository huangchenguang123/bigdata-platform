package com.chuxing.bdp.model.rpc.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2023/4/26 09:58
 * @author huangchenguang
 * @desc ad hot query request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdHotQueryRequest {

    /**
     * @date 2023/4/26 09:59
     * @desc sql
     */
    @NotBlank
    private String sql;

}
