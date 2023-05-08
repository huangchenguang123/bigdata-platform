/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.chuxing.bdp.service.ai;

import com.unfbx.chatgpt.entity.common.Usage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2023/5/6 11:38
 * @author huangchenguang
 * @desc ChatCompletionResponse
 */
@Data
public class ChatCompletionResponse implements Serializable {

    private static final long serialVersionUID = 4968922211204353592L;

    private String id;

    private String object;

    private long created;

    private String model;

    private List<ChatChoice> choices;

    private Usage usage;

}