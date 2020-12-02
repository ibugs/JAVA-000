package com.lesson007.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wanghao
 * 2020-12-02 14:10
 */
@Data
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;
}
