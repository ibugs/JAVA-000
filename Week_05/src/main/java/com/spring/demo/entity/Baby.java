package com.spring.demo.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wanghao
 * 2020-11-16 12:49
 */
@Component
public class Baby {

    @Value("张三的小孩")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
