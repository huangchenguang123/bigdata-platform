/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.chuxing.bdp.service.ai;

import com.chuxing.bdp.client.DuckDbFactory;
import com.chuxing.bdp.client.OpenApiFactory;
import com.chuxing.bdp.service.execute.ExecuteRouter;
import com.google.common.collect.Lists;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * @date 2023/5/8 10:49
 * @author huangchenguang
 * @desc AiService
 */
@Slf4j
@Service
public class AiService {

    @Resource
    private DuckDbFactory duckDbFactory;

    @Resource
    private ExecuteRouter executeRouter;

    /**
     * @date 2023/5/6 11:45
     * @desc open ai api timeout
     */
    private static final Integer MAX_WAIT_TIME = 10000;

    /**
     * @date 2023/5/6 11:45
     * @desc open ai api timeout
     */
    private static final Integer WAIT_COUNT = 10;

    /**
     * @date 2023/5/6 11:45
     * @desc open ai api timeout
     */
    private static final Integer ONE_WAIT_TIME = MAX_WAIT_TIME / WAIT_COUNT;

    /**
     * @date 2023/5/5 15:53
     * @author huangchenguang
     * @desc smart execute
     *
     * this method is copy from <a href="https://github.com/alibaba/Chat2DB">...</a>     */
    public List<List<String>> smartExecute(List<String> tables, String words) {
        String prompt = buildPrompt(tables, words);
        String sql = chatGpt35(prompt);
        try {
            return executeRouter.executeQuery(sql);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("query table fail, sql={}", sql, e);
            throw new RuntimeException("query table fail");
        }
    }

    /**
     * @date 2023/5/5 15:47
     * @author huangchenguang
     * @desc build prompt
     */
    private String buildPrompt(List<String> tables, String words) {
        Connection duckConnection = null;
        try {
            duckConnection = duckDbFactory.getConnection();
            Statement duckStatement = duckConnection.createStatement();

            StringBuilder columns = new StringBuilder();
            for (String table : tables) {
                String showTableSql = String.format("pragma table_info(%s);", table);
                ResultSet resultSet = duckStatement.executeQuery(showTableSql);
                StringBuilder columnBuilder = new StringBuilder();
                while (resultSet.next()) {
                    columnBuilder.append(String.format("%s, ", resultSet.getString(2)));
                }
                String column = columnBuilder.toString();
                columns.append(String.format("%s(%s)\n", table, column.substring(0, column.length() - 2)));
            }

            return String.format("### Please convert natural language to SQL queries according to the following table properties and SQL input. \n" +
                    "#\n" +
                    "### SQLITE SQL tables, with their properties:\n" +
                    "#\n" +
                    "#%s" +
                    "#\n" +
                    "#\n" +
                    "### SQL input: %s", columns, words);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("build prompt fail, tables={}, words={}", tables, words, e);
            throw new RuntimeException("build prompt fail");
        } finally {
            duckDbFactory.close(duckConnection);
        }
    }

    /**
     * @date 2023/5/5 15:35
     * @author huangchenguang
     * @desc chat to chatgpt
     */
    private String chatGpt35(String prompt) {
        try {
            OpenAiStreamClient openAiStreamClient = OpenApiFactory.getOpenAiStreamClient();
            if (Objects.isNull(openAiStreamClient)) {
                throw new RuntimeException("open ai key is empty, cant use smart execute");
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
            throw new RuntimeException("call chatgpt fail");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("smart execute fail, prompt={}", prompt, e);
            throw new RuntimeException("smart execute fail");
        }
    }

}
