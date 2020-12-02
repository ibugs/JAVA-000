package com.lesson007.demo;

import com.lesson007.bean.Person;
import com.lesson007.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 参考这里
 * https://github.com/smallCodeWangzh/application
 *
 * @author wanghao
 * 2020-12-02 14:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testWrite() {
        Person person = new Person();
        person.setName("zhangsan");
        person.setAge(18);
        personService.add(person);
    }

    @Test
    public void testQuery() {
        List<Person> all = personService.findAll();
        all.forEach(System.out::println);
    }

}
