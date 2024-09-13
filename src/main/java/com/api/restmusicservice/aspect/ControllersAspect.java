package com.api.restmusicservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ControllersAspect {

    @Pointcut("execution(* com.api.restmusicservice.controllers.MusicController.*(..))")
    public void allControllerMethods() {
    }

    @Around("allControllerMethods()")
    public Object aroundAllMusicControllerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // Получаем имя метода
        String methodName = joinPoint.getSignature().getName();

        // Получаем параметры метода
        Object[] args = joinPoint.getArgs();
        // Логируем имя метода и параметры
        log.info("Вызов метода: " + methodName);
//        log.info("Параметры метода: " + Arrays.toString(args));

        // Продолжаем выполнение метода
        Object result = joinPoint.proceed();


        log.info("Выход из метода: " + methodName);
        // Возвращаем результат выполнения метода
        return result;
    }
}
