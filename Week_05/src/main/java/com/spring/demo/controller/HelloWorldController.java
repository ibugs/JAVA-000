package com.spring.demo.controller;

import com.geek.service.HelloFormatTemplate;
import com.spring.demo.entity.Teacher;
import com.spring.demo.service.WorkDateImpl;
import com.spring.demo.service.WorkDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    WorkDateService workDateService;

    @RequestMapping("/hello")
    String home() {
        return teacher.toString();
    }

    @GetMapping("/format")
    public String main(String[] args) {
        return helloFormatTemplate.doFormat(teacher);
    }

    @GetMapping("/create")
    String create() {
        workDateService.create(20201212, 3, 4);
        return "创建成功";
    }

    @GetMapping("/delete")
    String delete(HttpServletRequest request) {
        Integer natureDate = Integer.parseInt(request.getParameter("nature_date"));
        int result = workDateService.delete(natureDate);
        if (result == 0) {
            return request+"，没有找到";
        } else {
            return "删除成功" + result + "条";
        }
    }

    @GetMapping("update")
    String update(HttpServletRequest request) {
        Integer natureDate = Integer.parseInt(request.getParameter("nature_date"));
        Integer workDateFlag = Integer.parseInt(request.getParameter("work_date_flag"));
        Integer tradeDateFlag = Integer.parseInt(request.getParameter("trade_date_flag"));

        int result = workDateService.update(natureDate, workDateFlag, tradeDateFlag);
        if (result == 0) {
            return natureDate + "," + workDateFlag + "," + tradeDateFlag + "-->" + "，没有找到";
        } else {
            return "更新成功" + result + "条";
        }
    }

    @GetMapping("queryAll")
    String queryAll() {
        return workDateService.query(1);
    }
}
