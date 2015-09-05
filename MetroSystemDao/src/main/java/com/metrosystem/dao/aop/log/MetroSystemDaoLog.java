package com.metrosystem.dao.aop.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;


public class MetroSystemDaoLog {

	private Logger log = Logger.getLogger(getClass());

	@Before("execution(* com.metrosystem.dao.**.*(..))"
			+ "|| execution(* com.metrosystem.service.**.*(..))")
	public void enter(JoinPoint point) {
		log.debug("MetroSystemDao Layer: Entered " + point.getSignature().getName());
	}

	@After("execution(* com.metrosystem.dao.**.*(..))"
			+ "|| execution(* com.metrosystem.service.**.*(..))")
	public void exit(JoinPoint point) {
		log.debug("MetroSystemDao Layer: Exited " + point.getSignature().getName());
	}

	@AfterThrowing(value = "execution(* com.metrosystem.dao.**.*(..))"
			+ "|| execution(* com.metrosystem.service.**.*(..))", throwing = "exp")
	public void error(JoinPoint point, Throwable exp) {
		log.debug("MetroSystemDao Layer: Error: " + point.getSignature().getName() + ": "
				+ exp.getMessage());
	}
}
