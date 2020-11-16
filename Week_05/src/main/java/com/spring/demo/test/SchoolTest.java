package com.spring.demo.test;

import com.spring.demo.entity.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanghao
 * 2020-11-16 15:30
 */
public class SchoolTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        School sh = (School)context.getBean("school");
        System.out.println(sh.getTeacher());
    }
}
