package com.sjar.search.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuxiangjian
 * 开始控制器
 */
@RestController
public class StartController {

    @RequestMapping("/")
    public String home(){
        return "Hello, HDFS!";
    }
}
