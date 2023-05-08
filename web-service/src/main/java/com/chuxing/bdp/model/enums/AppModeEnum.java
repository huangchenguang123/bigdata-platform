package com.chuxing.bdp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2023/4/23 17:33
 * @author huangchenguang
 * @desc app mode
 */
@Getter
@AllArgsConstructor
public enum AppModeEnum {

    /**
     * @date 2023/4/23 17:34
     * @desc standalone
     */
    STANDALONE("standalone");

    /**
     * @date 2023/4/23 17:33
     * @desc mode
     */
    private final String mode;

}
