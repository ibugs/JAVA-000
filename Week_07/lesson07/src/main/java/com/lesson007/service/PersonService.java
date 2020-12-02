package com.lesson007.service;

import com.lesson007.config.Read;
import com.lesson007.config.Write;
import com.lesson007.bean.Person;
import com.lesson007.dao.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghao
 * 2020-12-02 14:13
 */
@Service
public class PersonService {
    @Autowired
    private PersonMapper personMapper;

    /***
     * 代表该方法对数据库的操作是一个写操作
     * @param person
     */
    @Write
    public void add(Person person) {
        personMapper.insert(person);
    }

    /***
     * 代表该方法对数据库的操作是一个读操作
     * @return
     */
    @Read
    public List<Person> findAll() {
        return personMapper.selectAll();
    }
}
