package com.spring.demo.entity;

/**
 * @author wanghao
 * 2020-11-16 19:16
 */
public class WorkDate {
    /**
     * 日期
     */
    private Integer natureDate;
    /**
     * 是否工作日
     */
    private Integer workDateFlag;
    /**
     * 是否交易日
     */
    private Integer tradeDateFlag;

    public Integer getNatureDate() {
        return natureDate;
    }

    public void setNatureDate(Integer natureDate) {
        this.natureDate = natureDate;
    }

    public Integer getWorkDateFlag() {
        return workDateFlag;
    }

    public void setWorkDateFlag(Integer workDateFlag) {
        this.workDateFlag = workDateFlag;
    }

    public Integer getTradeDateFlag() {
        return tradeDateFlag;
    }

    public void setTradeDateFlag(Integer tradeDateFlag) {
        this.tradeDateFlag = tradeDateFlag;
    }
}
