package com.geek.service.impl;

import com.alibaba.fastjson.JSON;
import com.geek.service.ISchool;

/**
 * 实现JSON 格式化文本
 * @author wanghao
 * 2020-11-16 16:18
 */
public class JsonFormatProcessor implements ISchool {

    @Override
    public <T> String ding(T obj) {
        return "使用Json format 进行格式化"+JSON.toJSONString(obj);
    }
}
