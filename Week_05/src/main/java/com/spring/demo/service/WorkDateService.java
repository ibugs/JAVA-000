package com.spring.demo.service;

/**
 * @author wanghao
 * 2020-11-16 19:21
 */
public interface WorkDateService {
    int create(Integer natureDate, Integer workDateFlag, Integer tradeDateFlag);

    int delete(Integer natureDate);

    int update(Integer natureDate, Integer workDateFlag, Integer tradeDateFlag);

    String query(Integer natureDate);
}
