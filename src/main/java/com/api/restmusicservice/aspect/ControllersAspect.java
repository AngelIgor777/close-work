package com.api.restmusicservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

        // Логируем имя метода и параметры до его выполнения
        log.info("Вызов метода: {} с параметрами: {}", methodName, Arrays.toString(args));

        Object result;

        try {
            // Выполняем метод
            result = joinPoint.proceed();
            // Логируем успешный выход из метода
            log.info("Метод {} выполнен успешно", methodName);
        } catch (Exception e) {
            // Логируем исключение
            log.error("Исключение в методе {}. Причина: {}. Аргументы метода: {}", methodName, e.getMessage(), Arrays.toString(args));
            throw e;  // Перебрасываем исключение дальше
        }

        return result;  // Возвращаем результат выполнения метода
    }
}
