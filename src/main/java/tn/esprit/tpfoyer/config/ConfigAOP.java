package tn.esprit.tpfoyer.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ConfigAOP {

    @Before("execution(* tn.esprit.tpfoyer.service.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("Entrée dans la méthode : {}", joinPoint.getSignature().getName());
    }

    @After("execution(* tn.esprit.tpfoyer.service.*.add*(..))")
    public void logMethodOut(JoinPoint joinPoint) {
        log.info("Exécution réussie de la méthode : {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* tn.esprit.tpfoyer.service.*.*(..))")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object obj = pjp.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Temps d'exécution de {} : {} millisecondes", pjp.getSignature().getName(), elapsedTime);

        return obj;
    }
}
