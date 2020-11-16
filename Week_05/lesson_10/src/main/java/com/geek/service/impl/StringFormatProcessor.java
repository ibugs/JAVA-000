package com.geek.service.impl;

import com.geek.service.ISchool;

/**
 * 使用字符串来进行格式化显示
 * @author wanghao
 * 2020-11-16 16:21
 */
public class StringFormatProcessor implements ISchool {

    @Override
    public <T> String ding(T obj) {
        return "使用字符串进行格式化"+ obj.toString();
    }
}
