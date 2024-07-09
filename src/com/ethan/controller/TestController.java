package com.ethan.controller;

import com.ethan.annotation.Controller;
import com.ethan.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public String index() {
        System.out.println("test -> index");
        // 模拟MVC返回的view
        return "";
    }

    @RequestMapping("hello")
    public String hello() {
        System.out.println("test -> hello");
        return "";
    }
}
