/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.chuxing.bdp.service.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @date 2023/5/6 11:38
 * @author huangchenguang
 * @desc OpenAIEventSourceListener
 */
@Slf4j
@Getter
public class OpenAIEventSourceListener extends EventSourceListener {

    private Boolean success;

    private final StringBuilder textBuilder;
    
    public OpenAIEventSourceListener() {
        textBuilder = new StringBuilder();
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
    }

    @SneakyThrows
    @Override
    public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
        if ("[DONE]".equals(data)) {
            success = true;
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        ChatCompletionResponse completionResponse = mapper.readValue(data, ChatCompletionResponse.class); // 读取Json
        String text = completionResponse.getChoices().get(0).getDelta() == null
            ? completionResponse.getChoices().get(0).getText()
            : completionResponse.getChoices().get(0).getDelta().getContent();
        if (Objects.nonNull(text)) {
            textBuilder.append(text);
        }
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
    }
    
    @Override
    @SneakyThrows
    public void onFailure(@NotNull EventSource eventSource, Throwable t, Response response) {
        success = false;
    }

}
