package com.aristotle.admin.service.security;


import com.aristotle.admin.controller.beans.UserSessionObject;
import com.aristotle.admin.exception.NotAllowedException;
import com.aristotle.admin.service.LoginService;
import com.aristotle.core.enums.AppPermission;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class SecurityAspect {

    @Autowired
    private LoginService loginService;

    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {
    }

    @Before("anyPublicMethod() && @annotation(secureService)")// the pointcut expression
    public void securityPointCut(JoinPoint joinPoint, SecureService secureService) throws NotAllowedException {
        log.info("Runnign security for method {}", joinPoint.getSignature().getName());
        log.info("Runnign security for method {}", loginService);
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserSessionObject userSessionObject = loginService.getUserSessionObject(httpServletRequest);
        if (userSessionObject == null) {
            throw new NotAllowedException("User not logged in");
        }
        if (userSessionObject.getUser().isSuperAdmin()) {
            return;
        }
        Set<AppPermission> userAppPermissions = userSessionObject.getUserPermissionBean().getAppPermissions();
        if (userAppPermissions == null) {
            throw new NotAllowedException(userSessionObject.getUser().getName() + " is not allowed for this operation and this incident has been reported");
        }
        AppPermission[] methodAppPermissions = secureService.permissions();
        for (AppPermission oneMethodPermission : methodAppPermissions) {
            if (userAppPermissions != null && userAppPermissions.contains(oneMethodPermission)) {
                return;
            }
        }
        throw new NotAllowedException(userSessionObject.getUser().getName() + " is not allowed for this operation and this incident has been reported");
    }


    @Around("anyPublicMethod() && @annotation(secureService)")
    public Object process(ProceedingJoinPoint jointPoint, SecureService secureService) throws Throwable {
        System.out.println("In AOP process");
        return jointPoint.proceed();
    }
}
