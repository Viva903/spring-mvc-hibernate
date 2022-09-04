package com.viva903.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMloggingAspect {

//	setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

//	setup pointcut expression
	@Pointcut("execution(* com.viva903.springdemo.controller.*.*(..))")
	private void forControllerPackage() {

	};

	@Pointcut("execution(* com.viva903.springdemo.service.*.*(..))")
	private void forServicePackage() {

	};

	@Pointcut("execution(* com.viva903.springdemo.dao.*.*(..))")
	private void forDAOPackage() {

	};

	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {

	}

//	add @before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
//		displaye methods we are calling;
		String methodSig = theJoinPoint.getSignature().toLongString();
		myLogger.info("@Before ====> CRMloggingAspect Methods : " + methodSig);
		
//		display the arguments to the advice
		Object[] methodArgs = theJoinPoint.getArgs();
		for (Object args : methodArgs) {
			myLogger.info("@Before ====> CRMloggingAspect Arguments: " + args);
		}
	}

//	add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

//		display the method we are returning from
		String methodSig = theJoinPoint.getSignature().toLongString();
		myLogger.info("@AfterReturning ====> CRMloggingAspect Methods : " + methodSig);
		
//		display data returned
		myLogger.info("@AfterReturning ====> results : " + theResult);
	}

}
