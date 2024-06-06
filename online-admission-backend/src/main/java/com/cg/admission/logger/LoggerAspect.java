package com.cg.admission.logger;
 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class LoggerAspect { //aspect
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);
	// return type, class name, method name, arguments
	@Before("execution(* com.cg.admission.service..*(..))") //advice pointcut
	public void logMethodCall(JoinPoint joinPoint) {
		LOGGER.info("method called " + joinPoint.getSignature().getName());
	}
	@AfterReturning("execution(* com.cg.admission.service..*(..))")
	public void logMethodExecutedSuccessfully(JoinPoint joinPoint) {
		LOGGER.info("method executed successfully" + joinPoint.getSignature().getName());
	}
	@AfterThrowing("execution(* com.cg.admission.service..*(..))")
	public void logMethodCrash(JoinPoint joinPoint) {
		LOGGER.info("error thrown at " + joinPoint.getSignature().getName());
	}
}
