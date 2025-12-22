package com.ssdjr2.pd.product.config.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Around("@annotation(LogExec)")
	public Object logHttpLifecycle(final ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		Object[] args = joinPoint.getArgs();

		log.info(">>> REQ | Class: {} | Method: {} | Args: {}", className, methodName, args);
		long startTime = System.currentTimeMillis();

		try {
			Object result = joinPoint.proceed();
			long executionTime = System.currentTimeMillis() - startTime;

			log.info("<<< RESP | Class: {} | Method: {} | Resul: {} | Time: {}ms", className, methodName, result,
					executionTime);

			return result;
		} catch (Exception e) {
			log.error("!!! ERROR | Class: {} | Method: {} | Msg: {}", className, methodName, e.getMessage());
			throw e;
		}
	}
}
