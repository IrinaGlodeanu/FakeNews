package com.fakenews.service;

import com.fakenews.entities.User;
import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
public class AOPService {

    @Autowired
    private SecurityService securityService;

    @Around("execution(* com.fakenews.service.UserService.save(..)))")
    public void logceva(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = (User) joinPoint.getArgs()[0];

        String hashedEmail = securityService.encryptString(user.getEmail());

        user.setEmail(hashedEmail);

        joinPoint.proceed(Lists.newArrayList(user).toArray());
    }

    @Around("execution(* com.fakenews.service.*.*(..))")
    public Object computePerformance(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long time = System.currentTimeMillis() - start;

        System.out.println("Method execution time: " + time + " millis for method " + pjp.getSignature().getName());

        return output;
    }
}
