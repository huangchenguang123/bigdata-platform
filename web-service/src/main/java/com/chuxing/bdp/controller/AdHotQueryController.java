package com.chuxing.bdp.controller;

import com.chuxing.bdp.model.rpc.common.Result;
import com.chuxing.bdp.model.rpc.request.AdHotQueryRequest;
import com.chuxing.bdp.model.rpc.request.AdHotSmartQueryRequest;
import com.chuxing.bdp.service.ai.AiService;
import com.chuxing.bdp.service.execute.ExecuteRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @date 2023/4/26 09:53
 * @author huangchenguang
 * @desc ad hot query controller
 */
@Slf4j
@RestController
@RequestMapping("/ad-hot-query")
public class AdHotQueryController {

    @Resource
    private ExecuteRouter executeRouter;

    @Resource
    private AiService aiService;

    /**
     * @date 2023/4/26 11:47
     * @author huangchenguang
     * @desc ad hot query execute
     */
    @RequestMapping("/execute")
    public Result<List<List<String>>> execute(@RequestBody @Valid AdHotQueryRequest adHotQueryRequest) {
        return Result.success(executeRouter.executeQuery(adHotQueryRequest.getSql()));
    }

    /**
     * @date 2023/4/26 11:47
     * @author huangchenguang
     * @desc ad hot query smart execute
     */
    @RequestMapping("/smartExecute")
    public Result<List<List<String>>> smartExecute(@RequestBody @Valid AdHotSmartQueryRequest adHotSmartQueryRequest) {
        return Result.success(aiService.smartExecute(adHotSmartQueryRequest.getTables(), adHotSmartQueryRequest.getQuery()));
    }

}
