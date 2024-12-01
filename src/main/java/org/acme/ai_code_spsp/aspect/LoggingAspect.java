package org.acme.ai_code_spsp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs all public methods in the repository layer.
     */
    @Around("execution(* org.acme.ai_code_spsp.repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.debug("Entering Repository method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.debug("Repository method: {} returned: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Error in Repository method: {} with exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }

    /**
     * Logs all public methods in the service layer.
     */
    @Around("execution(* org.acme.ai_code_spsp.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering Service method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.info("Service method: {} returned: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Error in Service method: {} with exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }

    @Around("execution(* org.acme.ai_code_spsp.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering Controller method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.info("Controller method: {} returned: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Error in Controller method: {} with exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }

    @Around("execution(* org.acme.ai_code_spsp.security..*(..))")
    public Object logSecurityMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering Security method: {} with arguments: {}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            logger.info("Security method: {} returned: {}", methodName, result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Error in Security method: {} with exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }


}
