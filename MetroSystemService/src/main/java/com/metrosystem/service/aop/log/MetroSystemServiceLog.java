package com.metrosystem.service.aop.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MetroSystemServiceLog {

	private Logger log = Logger.getLogger(getClass());
	
	@Before("execution(* com.metrosystem.dao.**.*(..))"
			+ "|| execution(* com.metrosystem.service.**.*(..))")
	public void enter(JoinPoint point) {
		log.debug("Entered " + point.getTarget().getClass() + "." +  point.getSignature().getName());
	}

	@After("execution(* com.metrosystem.dao.**.*(..))"
			+ "|| execution(* com.metrosystem.service.**.*(..))")
	public void exit(JoinPoint point) {
		log.debug("Exited " + point.getTarget().getClass() + "." +  point.getSignature().getName());
	}

	@AfterThrowing(value = "execution(* com.metrosystem.dao.**.*(..))"
			+ "|| execution(* com.metrosystem.service.**.*(..))", throwing = "exp")
	public void error(JoinPoint point, Throwable exp) {
		log.debug("Error: " + point.getTarget().getClass() + "." +  point.getSignature().getName()
				+ ": " + exp.getMessage());
	}
}
