package com.chuxing.bdp.model.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @date 2022/11/24 17:09
 * @author huangchenguang
 * @desc rpc common result
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
     * @desc result
     */
    private T data;

    /**
     * @date 2022/11/24 17:11
     * @desc msg
     */
    private String msg;

    /**
     * @date 2022/11/24 17:11
     * @desc code
     */
    private Integer code;

    /**
     * @date 2022/11/24 17:32
     * @author huangchenguang
     * @desc success
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
     * @desc fail
     */
    public static <T> Result<T> fail(String msg) {
        return Result.<T>builder()
                .data(null)
                .code(500)
                .msg(msg)
                .build();
    }

}
