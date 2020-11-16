package com.spring.demo.test;

import com.spring.demo.entity.Teacher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanghao
 * 2020-11-16 12:41
 */
public class TeacherTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        Teacher teacher = (Teacher) context.getBean("teacher");
        System.out.println(teacher);

        Teacher teacher2 = (Teacher)context.getBean("teacher2");
        System.out.println(teacher2.getBaby().getName());
    }
}
