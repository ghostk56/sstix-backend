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
@Order(2)
@Component
public class TestAspect2 {

	@Pointcut("execution(public * com.sstixbackend..*Controller.*(..))")
	public void testPoint2() {
	}

	@Before("testPoint2()")
	public void before() {
		System.out.println("before2");
	}

	@Around("testPoint2()")
	public Object inputInterceptor(ProceedingJoinPoint pjp) throws Throwable {

		System.out.println("testPoint2-1");
		Object proceed = pjp.proceed();
		System.out.println("testPoint2-2");
		return proceed;
	}

	@After("testPoint2()")
	public void after() {
		System.out.println("after2");
	}

	@AfterThrowing(pointcut = "testPoint2()", throwing = "exception")
	public void afterThrowing(Exception exception) {
		System.out.println("AfterThrowing2");
	}

}
