package com.chuxing.bdp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2023/4/23 17:33
 * @author huangchenguang
 * @desc 应用运行模式
 */
@Getter
@AllArgsConstructor
public enum AppModeEnum {

    /**
     * @date 2023/4/23 17:34
     * @desc 独立模式
     */
    STANDALONE("standalone");

    /**
     * @date 2023/4/23 17:33
     * @desc 模式
     */
    private final String mode;

}
