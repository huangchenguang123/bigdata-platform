/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.chuxing.bdp.service.ai;

import com.chuxing.bdp.client.OpenApiFactory;
import com.chuxing.bdp.service.execute.ExecuteRouter;
import com.google.common.collect.Lists;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * @date 2023/5/8 10:49
 * @author huangchenguang
 * @desc ai服务
 */
@Slf4j
@Service
public class AiService {

    @Resource
    private ExecuteRouter executeRouter;

    /**
     * @date 2023/5/6 11:45
     * @desc open ai api 总超时时间
     */
    private static final Integer MAX_WAIT_TIME = 10000;

    /**
     * @date 2023/5/6 11:45
     * @desc open ai api 等待次数
     */
    private static final Integer WAIT_COUNT = 10;

    /**
     * @date 2023/5/6 11:45
     * @desc open ai api 单次等待时间
     */
    private static final Integer ONE_WAIT_TIME = MAX_WAIT_TIME / WAIT_COUNT;

    /**
     * @date 2023/5/5 15:53
     * @author huangchenguang
     * @desc 自然语言查询
     *
     * 这个方法是从chat2db中搬运修改的 <a href="https://github.com/alibaba/Chat2DB">...</a>     */
    public List<List<String>> smartExecute(List<String> tables, String words) {
        String prompt = buildPrompt(tables, words);
        String sql = chatGpt35(prompt);
        try {
            return executeRouter.executeQuery(sql);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("自然语言查询失败, words={}, sql={}", words, sql, e);
            throw new RuntimeException("自然语言查询失败");
        }
    }

    /**
     * @date 2023/5/5 15:47
     * @author huangchenguang
     * @desc 构建提示词
     */
    private String buildPrompt(List<String> tables, String words) {
        try {
            StringBuilder columnsStr = new StringBuilder();
            for (String table : tables) {
                List<String> columns = executeRouter.getColumns(table);
                String column = StringUtils.join(columns, ", ");
                columnsStr.append(String.format("%s(%s)\n", table, column.substring(0, column.length() - 2)));
            }

            return String.format("### Please convert natural language to SQL queries according to the following table properties and SQL input. \n" +
                    "#\n" +
                    "### %s SQL tables, with their properties:\n" +
                    "#\n" +
                    "#%s" +
                    "#\n" +
                    "#\n" +
                    "### SQL input: %s", executeRouter.getEngineType(), columnsStr, words);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("构建提示词失败, 数据集={}, 自然语言={}", tables, words, e);
            throw new RuntimeException("构建提示词失败");
        }
    }

    /**
     * @date 2023/5/5 15:35
     * @author huangchenguang
     * @desc 与chat gpt交互
     */
    private String chatGpt35(String prompt) {
        try {
            OpenAiStreamClient openAiStreamClient = OpenApiFactory.getOpenAiStreamClient();
            if (Objects.isNull(openAiStreamClient)) {
                throw new RuntimeException("open ai初始化异常");
            }

            Message currentMessage = Message.builder().content(prompt).role(Message.Role.USER).build();
            OpenAIEventSourceListener openAiEventSourceListener = new OpenAIEventSourceListener();
            openAiStreamClient.streamChatCompletion(Lists.newArrayList(currentMessage), openAiEventSourceListener);
            long time = ONE_WAIT_TIME;
            while (Objects.isNull(openAiEventSourceListener.getSuccess())) {
                ThreadUtils.sleep(Duration.ofMillis(ONE_WAIT_TIME));
                time += ONE_WAIT_TIME;
                if (time > MAX_WAIT_TIME) {
                    break;
                }
            }
            if (BooleanUtils.isTrue(openAiEventSourceListener.getSuccess())) {
                return openAiEventSourceListener.getTextBuilder().toString();
            }
            throw new RuntimeException("查询open ai超时");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询open ai失败, 提示词={}", prompt, e);
            throw new RuntimeException("查询open ai失败");
        }
    }

}
