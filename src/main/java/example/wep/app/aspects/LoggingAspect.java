package example.wep.app.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before(
            "execution(* example.wep.app.service.*.*(..))"
    )
    public void logMethod(
            JoinPoint joinPoint
    ) {

        log.info(
                "Executing method: {}",
                joinPoint.getSignature().getName()
        );
    }
}
