package com.hientran.sohebox.configuration;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Application log plan
 *
 * @author hientran
 */
@Aspect
@Configuration
public class AspectLogger {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ///////////////////////////////////////////////////////
    // Configured log before - after for rest controller //
    ///////////////////////////////////////////////////////
    @Before(value = "execution(* com.hientran.sohebox.restcontroller.*.*(..))")
    public void beforeRestful(JoinPoint joinPoint) {
        logger.info("    START     : {}", joinPoint.getSignature());
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            logger.info("    Arguments : {}", Arrays.asList(joinPoint.getArgs()).toString());
        }
    }

    @AfterReturning(value = "execution(* com.hientran.sohebox.restcontroller.*.*(..))", returning = "result")
    public void afterRestful(JoinPoint joinPoint, Object result) {
        if (result != null) {
            logger.info("     Returned  : {}", result);
        }
        logger.info("     END       : {}", joinPoint.getSignature());
    }

    ////////////////////////////////////////////////
    // Configured log before - after for services //
    ////////////////////////////////////////////////
    @Before(value = "execution(* com.hientran.sohebox.service.*.*(..))")
    public void beforeService(JoinPoint joinPoint) {
        logger.info("    START     : {}", joinPoint.getSignature());
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            logger.info("    Arguments : {}", Arrays.asList(joinPoint.getArgs()).toString());
        }
    }

    @AfterReturning(value = "execution(* com.hientran.sohebox.service.*.*(..))", returning = "result")
    public void afterService(JoinPoint joinPoint, Object result) {
        if (result != null) {
            // logger.info(" Returned : {}", result);
        }
        logger.info("     END       : {}", joinPoint.getSignature());
    }

    //////////////////////////////////////////////////
    // Configured log before - after for repository //
    //////////////////////////////////////////////////
    @Before(value = "execution(* com.hientran.sohebox.repository.*.*(..))")
    public void beforeRepository(JoinPoint joinPoint) {
//        logger.info(" START     : {}", joinPoint.getSignature());
//        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
//            logger.info(" Arguments : {}", Arrays.asList(joinPoint.getArgs()).toString());
//        }
    }

    @AfterReturning(value = "execution(* com.hientran.sohebox.repository.*.*(..))", returning = "result")
    public void afterRepository(JoinPoint joinPoint, Object result) {
//        logger.info("  END       : {}", joinPoint.getSignature());
    }

    ///////////////////////////////////////////////////
    // Configured log before - after for transformer //
    ///////////////////////////////////////////////////
    @Before(value = "execution(* com.hientran.sohebox.transformer.*.*(..))")
    public void beforeTransformer(JoinPoint joinPoint) {
//        logger.info("START     : {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.hientran.sohebox.transformer.*.*(..))", returning = "result")
    public void afterTransformer(JoinPoint joinPoint, Object result) {
//        logger.info(" END       : {}", joinPoint.getSignature());
    }

    ///////////////////////////////////////////////////
    // Configured log before - after for scheduler //
    ///////////////////////////////////////////////////
    @Before(value = "execution(* com.hientran.sohebox.scheduler.*.*(..))")
    public void beforeScheduler(JoinPoint joinPoint) {
        logger.info("  START SCHEDULER : {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.hientran.sohebox.scheduler.*.*(..))", returning = "result")
    public void afterScheduler(JoinPoint joinPoint, Object result) {
        logger.info("   END SCHEDULER : {}", joinPoint.getSignature());
    }
}