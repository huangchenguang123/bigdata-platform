package com.chuxing.bigdataplatform.adhocquery.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2022/5/25 13:53
 * @author huangchenguang
 * @desc ad hoc query controller
 */
@RestController
@RequestMapping("/ad-hoc-query")
public class AdHocQueryController {

    @RequestMapping("/test")
    public String test() {
        return "hello world!";
    }

}
