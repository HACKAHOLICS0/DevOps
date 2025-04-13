package tn.esprit.tpfoyer.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer.config.ConfigAOP;

import static org.mockito.Mockito.*;

 class ConfigAOPTest {

    ConfigAOP configAOP = new ConfigAOP();

    @Test
     void testLogMethodEntry() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");

        configAOP.logMethodEntry(joinPoint);
    }

    @Test
    void  testProfile() throws Throwable {
        ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
        when(pjp.proceed()).thenReturn("result");

        Object result = configAOP.profile(pjp);
        assert result.equals("result");
    }
}
