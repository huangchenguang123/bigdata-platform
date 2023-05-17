/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.chuxing.bdp.client;

import com.chuxing.bdp.config.AppConfig;
import com.chuxing.bdp.utils.SpringContextUtil;
import com.google.common.collect.Lists;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.constant.OpenAIConst;
import org.apache.commons.lang3.StringUtils;

/**
 * @date 2023/5/6 10:20
 * @author huangchenguang
 * @desc open ai工厂类，用于创建open ai客户端
 */
public class OpenApiFactory  {

    /**
     * @date 2023/5/6 10:21
     * @author huangchenguang
     * @desc 初始化open ai客户端
     */
    public static OpenAiStreamClient getOpenAiStreamClient() {
        AppConfig appConfig = SpringContextUtil.getBean(AppConfig.class);
        String key = appConfig.getApiKey();
        if (StringUtils.isNotBlank(key)) {
            return OpenAiStreamClient.builder().apiHost(OpenAIConst.OPENAI_HOST).apiKey(Lists.newArrayList(appConfig.getApiKey())).build();
        }
        return null;
    }

}
