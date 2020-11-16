学习笔记

#### 第九课
##### 1、（选做）使Java里的动态代理，实现一个简单的AOP。
##### 2、（必做）写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）,提交到Github。

a. 使用XML的方式装配，setter的方式注入
在resources添加 applicationContext.xml，然后在其中添加bean的描述

在Student类中添加set方法

```
  <bean id="student123"
        class="com.spring.demo.entity.Student">
      <property name="age" value="22" />
      <property name="name" value="张三" />
  </bean>
```

在main方法中调用
```
   ApplicationContext context =
           new ClassPathXmlApplicationContext("applicationContext.xml");
   Student student123 = (Student) context.getBean("student123");
   System.out.println(student123);
```

b. 使用构造器的方式注入

定义有两个参数的构造器
```
    public Student(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
```

添加构造器配置的XML

```
   <bean id = "student222" class="com.spring.demo.entity.Student">
       <constructor-arg name="age" value="99"></constructor-arg>
       <constructor-arg name="name" value="空林"></constructor-arg>
   </bean>

```

main 方法调用
```
   Student student222 = (Student) context.getBean("student222");
   System.out.println(student222);
```


c. 使用Annotation的方式来装配

先在resources添加 applicationContext.xml，然后添加bean扫描
```
  <context:component-scan base-package="com.spring.demo" />
```

实体类里面使用注解的标签来进行赋值
```
@Component
public class Teacher {

    @Value("12")
    Integer age;

    @Value("张老师")
    String name;

    @Value("123.2")
    Double salary;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

```

在main方法中调用
```
   ApplicationContext context =
           new ClassPathXmlApplicationContext("applicationContext.xml");

   Teacher teacher = (Teacher) context.getBean("teacher");
   System.out.println(teacher);
```

d. 自动装配

使用@Autowired来自动装配

```
    @Component
    public class School {
        @Autowired
        Teacher teacher;
    
        public Teacher getTeacher() {
            return teacher;
        }
    
        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }
    }
```
在main方法中调用

```
   ApplicationContext context =
           new ClassPathXmlApplicationContext("applicationContext.xml");

   School sh = (School)context.getBean("school");
   System.out.println(sh.getTeacher());
```


##### 3、（选做）实现一个Spring XML自定义配置，配置一组Bean，例如Student/Klass/School。
##### 4、（选做，会添加到高手附加题）
######## 4.1 （挑战）讲网关的frontend/backend/filter/router/线程池都改造成Spring配置方式；
######## 4.2 （挑战）基于AOP改造Netty网关，filter和router使用AOP方式实现；
######## 4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用JMS传递消息；
######## 4.4 （中级挑战）尝试使用ByteBuddy实现一个简单的基于类的AOP；
######## 4.5 （超级挑战）尝试使用ByteBuddy与Instrument实现一个简单JavaAgent实现无侵入下的 AOP；

#### 第十课
##### 1. （选做）总结一下，单例的各种写法，比较它们的优劣。
##### 2. （选做）maven/spring 的 profile 机制，都有什么用法？
##### 3. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

starter 的功能，使用字符串和JSON的格式来格式化对象

a. starter 实现 [自定义starter实现](./lesson_10/src/main/java/com/geek/service/HelloAutoConfiguration.java)

b. mvn install

c. 在pom文件中配置使用
```
  <dependency>
      <groupId>schoolG</groupId>
      <artifactId>shoolA</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
```

d. 编写Controller调用

```
@RestController
@EnableAutoConfiguration
public class HelloWorldController {
    @Autowired
    HelloFormatTemplate helloFormatTemplate;
    @Autowired
    Teacher teacher;

    @GetMapping("/format")
    public String main(String[] args) {
        return helloFormatTemplate.doFormat(teacher);
    }
}
```
e. 使用默认参数调用
http://localhost:8981/format
```
Obj format result使用字符串进行格式化Teacher{age=12, name='张老师', salary=123.2}
begin: Execute formate
```

f. 在pom文件中添加fastjson，使用JSON来格式化字符串

```
  <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>1.2.72</version>
  </dependency>
```
再访问 http://localhost:8981/format, 使用JSON来格式化字符串

```
Obj format result使用Json format 进行格式化{"age":12,"baby":{"name":"张三的小孩"},"name":"张老师","salary":123.2}
begin: Execute formate
```

总结：
+ starter 减少重复的工作，相当于一个小轮子
+ pom配置的时候，注意版本

参考:

[springboot自定义starter实现](https://www.cnblogs.com/wuzhenzhao/p/11586464.html)

[手把手教你实现自定义 Spring Boot 的 Starter](https://xie.infoq.cn/article/68621c5f5c1dc16312e0a52e4)


##### 4. （选做）总结 Hibernate 与 MyBatis 的各方面异同点。
##### 5. （选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。
##### 6. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：

  [代码](./src/main/java/com/jdbc/demo/Test.java)
  [Impl代码](./src/main/java/com/spring/demo/service/WorkDateImpl.java)

  1）使用 JDBC 原生接口，实现数据库的增删改查操作。

  2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。

  3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。

##### 附加题（可以后面上完数据库的课再考虑做）：
1. (挑战)基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存60秒。
2. (挑战)自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。
3. (挑战)基于 MyBatis 实现一个简单的分库分表+读写分离+分布式 ID 生成方案。
