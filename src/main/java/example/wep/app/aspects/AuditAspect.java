package example.wep.app.aspects;


import example.wep.app.entity.AuditLog;
import example.wep.app.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditLogRepository auditLogRepository;

    @AfterReturning("execution(* example.wep.app.service..*(..)) && !execution(* example.wep.app.service.JwtService.*(..))")
    public void logSuccess(JoinPoint joinPoint) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = (auth != null && auth.isAuthenticated())
                ? auth.getName()
                : "ANONYMOUS";

        String method =
                joinPoint.getSignature().getName();

        auditLogRepository.save(
                AuditLog.builder()
                        .username(username)
                        .method(method)
                        .action("SUCCESS")
                        .details("Executed " + method)
                        .build());
    }
}
