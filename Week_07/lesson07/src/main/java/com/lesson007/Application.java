package com.lesson007;

import com.lesson007.bean.Person;
import com.lesson007.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wanghao
 * 2020-12-02 14:16
 */
@SpringBootApplication
@MapperScan("com.lesson007.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
