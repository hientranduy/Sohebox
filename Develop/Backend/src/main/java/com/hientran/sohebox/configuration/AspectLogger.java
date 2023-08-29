package com.hientran.sohebox.configuration;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class AspectLogger {

	///////////////////////////////////////////////////////
	// Configured log before - after for rest controller //
	///////////////////////////////////////////////////////
	@Before(value = "execution(* com.hientran.sohebox.restcontroller.*.*(..))")
	public void beforeRestful(JoinPoint joinPoint) {
		log.info("START {} - Arguments : {}", joinPoint.getSignature().toShortString(),
				Arrays.asList(joinPoint.getArgs()).toString());
	}

	@AfterReturning(value = "execution(* com.hientran.sohebox.restcontroller.*.*(..))", returning = "result")
	public void afterRestful(JoinPoint joinPoint, Object result) {
		log.info("END   {} - Returned  : {}", joinPoint.getSignature().toShortString(), result);
	}

	///////////////////////////////////////////////////
	// Configured log before - after for scheduler //
	///////////////////////////////////////////////////
	@Before(value = "execution(* com.hientran.sohebox.scheduler.*.*(..))")
	public void beforeScheduler(JoinPoint joinPoint) {
		log.info("START SCHEDULER : {}", joinPoint.getSignature().toShortString());
	}

	@AfterReturning(value = "execution(* com.hientran.sohebox.scheduler.*.*(..))", returning = "result")
	public void afterScheduler(JoinPoint joinPoint, Object result) {
		log.info("END SCHEDULER : {}", joinPoint.getSignature().toShortString());
	}
}