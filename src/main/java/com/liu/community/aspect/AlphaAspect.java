package com.liu.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {

    //execution(任意返回值 包名中所有的方法)； 即所有的业务层的方法；
    @Pointcut("execution(* com.liu.community.service.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")//在切点开始之前就处理
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()")//在得到返回值以后处理
    public void afterRetuning() {
        System.out.println("afterRetuning");
    }

    @AfterThrowing("pointcut()")//在抛异常的时候执行
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")//既想在前面也想在后面
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }
    //顺序
    //around before
    //before
    // around after
    //after
    //afterRetuning


}
