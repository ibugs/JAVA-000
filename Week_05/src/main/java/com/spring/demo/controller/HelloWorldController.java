package com.spring.demo.controller;

import com.geek.service.HelloFormatTemplate;
import com.spring.demo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * 2020-11-16 13:03
 */
@RestController
@EnableAutoConfiguration
public class HelloWorldController {
    @Autowired
    HelloFormatTemplate helloFormatTemplate;
    @Autowired
    Teacher teacher;

    @RequestMapping("/hello")
    String home(){
        return teacher.toString();
    }

    @GetMapping("/format")
    public String main(String[] args) {
        return helloFormatTemplate.doFormat(teacher);
    }
}
