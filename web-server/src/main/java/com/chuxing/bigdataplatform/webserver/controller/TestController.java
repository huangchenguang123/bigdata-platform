package com.chuxing.bigdataplatform.webserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2022/5/25 13:53
 * @author huangchenguang
 * @desc ad hoc query controller
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping()
    public String test() {
        return "hello world!";
    }

}
