package com.networkapplication.aspects;

import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.models.Auditing;
import com.networkapplication.models.File;
import com.networkapplication.models.User;
import com.networkapplication.repositories.AuditingRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Aspect
@Component
@RequiredArgsConstructor
public class Log {
    private final AuditingRepository auditingRepository;
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileServiceImp.createFile(..))&&args(user)", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result,User user) {
        System.out.println("done");
        System.out.println(user.getUsername());
    }

}
