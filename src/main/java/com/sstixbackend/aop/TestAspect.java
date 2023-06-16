package com.sstixbackend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class TestAspect {

	@Pointcut("execution(public * com.sstixbackend..*Controller.*(..))")
	public void testPoint() {
	}

	@Around("testPoint()")
	public Object inputInterceptor(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("Around");
		if (!pjp.equals(null))
			throw new Exception("test拋出錯誤");
		return pjp.proceed();
	}

//	@AfterThrowing(throwing = "err", pointcut = "testPoint()")
//	public void doAfterThrowing(Throwable err) throws Throwable {
//		System.out.println("AfterThrowing");
//	}
}
