package com.sstixbackend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

	@Pointcut("execution(public * com.sstixbackend..*Controller.*(..))")
	public void testPoint() {
	}

	@Around("testPoint()")
	public Object inputInterceptor(ProceedingJoinPoint pjp) {

		throw new RuntimeException("aop拋出錯誤");

	}

//	@AfterThrowing(throwing = "err", pointcut = "testPoint()")
//	public void doAfterThrowing(Throwable err) throws Throwable {
//		System.out.println("AfterThrowing");
//	}
}
