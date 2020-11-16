package com.geek.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.geek.service.ISchool;
import com.geek.service.impl.JsonFormatProcessor;
import com.geek.service.impl.StringFormatProcessor;

/**
 * 需要把这两个类根据Conditional 注解动态注入到 Spring的容器中
 *
 * @author wanghao
 * 2020-11-16 16:24
 */
@Configuration
public class FormatAutoConfiguration {

    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    @Bean
    @Primary
    public ISchool stringFormat() {
        return new StringFormatProcessor();
    }

    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    @Bean
    public ISchool jsonFormat() {
        return new JsonFormatProcessor();
    }

}
