package com.spring.demo.test;

import com.spring.demo.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanghao
 * 2020-11-16 12:30
 */
public class StudentTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student123 = (Student) context.getBean("student123");
        System.out.println(student123);
    }
}
