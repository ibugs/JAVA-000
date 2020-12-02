package com.lesson007.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wanghao
 * 2020-12-02 14:05
 */
@Aspect
@Component
public class DataSourceAOP {

    /***
     * 只要加了@Read注解的方法就是一个切入点
     */
    @Pointcut("@annotation(com.lesson007.config.Read)")
    public void readPointcut() {
    }

    @Pointcut("@annotation(com.lesson007.config.Write)")
    public void writePointcut() {
    }

    /***
     * 配置前通知，如果是readPoint 就切换数据源为从数据库
     */
    @Before("readPointcut()")
    public void readAdvise() {
        DynamicSwitchDBTypeUtil.slave();
    }

    /***
     * 配置前通知，如果wirtePoint就切换数据源为主数据库
     */
    @Before("writePointcut()")
    public void writeAdvise() {
        DynamicSwitchDBTypeUtil.master();
    }
}
