package com.geek.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.geek.service.ISchool;

/**
 * 动态注入
 * @author wanghao
 * 2020-11-16 16:35
 */
@Import(FormatAutoConfiguration.class)
@Configuration
public class HelloAutoConfiguration {
    @Bean
    public HelloFormatTemplate helloFormatTemplate(ISchool iSchool) {
        return new HelloFormatTemplate(iSchool);
    }
}
