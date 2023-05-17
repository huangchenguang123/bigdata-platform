package com.chuxing.bdp.model.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @date 2022/11/24 17:09
 * @author huangchenguang
 * @desc rpc调用包装类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * @date 2022/11/25 15:43
     * @desc serialVersionUID
     */
    private static final long serialVersionUID = 3858997476649437710L;

    /**
     * @date 2022/11/24 17:11
     * @desc 返回数据
     */
    private T data;

    /**
     * @date 2022/11/24 17:11
     * @desc 额外信息
     */
    private String msg;

    /**
     * @date 2022/11/24 17:11
     * @desc 返回码
     */
    private Integer code;

    /**
     * @date 2022/11/24 17:32
     * @author huangchenguang
     * @desc 成功
     */
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .data(data)
                .code(200)
                .msg("successful")
                .build();
    }

    /**
     * @date 2022/11/24 17:32
     * @author huangchenguang
     * @desc 失败
     */
    public static <T> Result<T> fail(String msg) {
        return Result.<T>builder()
                .data(null)
                .code(500)
                .msg(msg)
                .build();
    }

}
