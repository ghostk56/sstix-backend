package com.sstixbackend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class TestAspect1 {

	@Pointcut("execution(public * com.sstixbackend..*Controller.*(..))")
	public void testPoint1() {
	}

	@Before("testPoint1()")
	public void before() {
		System.out.println("before1");
	}

	@Around("testPoint1()")
	public Object inputInterceptor(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("testPoint1-1");
		Object proceed = pjp.proceed();
		System.out.println("testPoint1-2");
		return proceed;
	}

	@After("testPoint1()")
	public void after() {
		System.out.println("after1");
	}

	@AfterThrowing(pointcut = "testPoint1()", throwing = "exception")
	public void afterThrowing(Exception exception) {
		System.out.println("AfterThrowing1");
	}

}
