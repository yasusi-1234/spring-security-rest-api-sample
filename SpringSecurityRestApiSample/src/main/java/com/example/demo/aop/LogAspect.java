package com.example.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LogAspect {

	@Before("execution(* *..*.*Service.*(..))")
	public void startLog(JoinPoint jp) {
//		log.info("{} : {}", jp.getSignature().getName(), jp.getSignature().getDeclaringTypeName());
		log.info("メソッドの開始: {}", jp.getSignature());
		log.info("getArgs: {}", Arrays.toString(jp.getArgs()));
	}

	@AfterThrowing(value = "execution(* *..*.*Service.*(..))", throwing = "ex")
	public void afterThrowingLog(JoinPoint jp, Exception ex) {
		log.warn("例外発生: {}", jp.getSignature());
		log.warn("例外クラス名: {} ,例外メッセージ: {}", ex.getClass().getName(), ex.getMessage());
	}

	@AfterReturning(value = "execution(* *..*.*Service.*(..))", returning = "ret")
	public void afterReturningLog(JoinPoint jp, Object ret) {
		log.info("メソッド正常終了: {}", jp.getSignature());
		log.info("戻り値: {}", ret);
	}
}
