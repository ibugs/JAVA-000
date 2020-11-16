学习笔记

## 第九课
### 1、（选做）使Java里的动态代理，实现一个简单的AOP。
### 2、（必做）写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）,提交到Github。
1. 使用XML的方式装配
在resources添加 applicationContext.xml，然后在其中添加bean的描述

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

2. 使用Annotation的方式来装配

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

3. 第三种装配的方式，使用Autowired

```
@Component
public class Baby {

    @Value("张三的小孩")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

```
@Component
public class Teacher {

    @Value("12")
    Integer age;

    @Value("张老师")
    String name;

    @Value("123.2")
    Double salary;

    @Autowired
    Baby baby;

    ...
}

```

applicationContext.xml 文件中进行配置

```
    <context:component-scan base-package="com.spring.demo" />

    <bean id="teacher2" class="com.spring.demo.entity.Teacher"/>

```

使用main方法来调用

```
   Teacher teacher2 = (Teacher)context.getBean("teacher2");
   System.out.println(teacher2.getBaby().getName());
```

4. 使用spring-boot api来显示

```
  @RestController
  @EnableAutoConfiguration
  public class HelloWorldController {
      @Autowired
      Teacher teacher;
  
      @RequestMapping("/hello")
      String home(){
          return teacher.toString();
      }
  
  }
```

浏览器调用
http://localhost:8981/hello

```
    Teacher{age=12, name='张老师', salary=123.2}
```


### 3、（选做）实现一个Spring XML自定义配置，配置一组Bean，例如Student/Klass/School。
### 4、（选做，会添加到高手附加题）
#### 4.1 （挑战）讲网关的frontend/backend/filter/router/线程池都改造成Spring配置方式；
#### 4.2 （挑战）基于AOP改造Netty网关，filter和router使用AOP方式实现；
#### 4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用JMS传递消息；
#### 4.4 （中级挑战）尝试使用ByteBuddy实现一个简单的基于类的AOP；
#### 4.5 （超级挑战）尝试使用ByteBuddy与Instrument实现一个简单JavaAgent实现无侵入下的 AOP；

## 第十课
### 1. （选做）总结一下，单例的各种写法，比较它们的优劣。
### 2. （选做）maven/spring 的 profile 机制，都有什么用法？
### 3. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
### 4. （选做）总结 Hibernate 与 MyBatis 的各方面异同点。
### 5. （选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。
### 6. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
  1）使用 JDBC 原生接口，实现数据库的增删改查操作。
  2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
  3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。

### 附加题（可以后面上完数据库的课再考虑做）：
1. (挑战)基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存60秒。
2. (挑战)自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。
3. (挑战)基于 MyBatis 实现一个简单的分库分表+读写分离+分布式 ID 生成方案。
