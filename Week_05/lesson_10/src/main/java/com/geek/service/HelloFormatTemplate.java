package com.geek.service;

import com.geek.service.ISchool;

/**
 * @author wanghao
 * 2020-11-16 16:32
 */
public class HelloFormatTemplate {
    private ISchool iSchool;

    public HelloFormatTemplate(ISchool iSchool) {
        this.iSchool = iSchool;
    }

    public <T> String doFormat(T obj) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("begin: Execute formate").append("<br/>");
        stringBuffer.append("Obj format result").append(iSchool.ding(obj)).append("<br/>");
        return stringBuffer.toString();
    }


}
