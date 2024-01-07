package com.networkapplication.aspects;

import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.Auditing;
import com.networkapplication.models.File;
import com.networkapplication.models.User;
import com.networkapplication.repositories.AuditingRepository;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.UserRepository;
import com.networkapplication.services.Utils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class Log {
    private final AuditingRepository auditingRepository;
    private final FileRepository fileRepository;
    private final Utils utils;
    private final UserRepository userRepository;
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileService.checkIn(..)) && args(checkIn)", returning = "result")
    public void logCheckin(JoinPoint joinPoint, Object result, CheckInDTO checkIn) throws ResponseException {
        User user = utils.getCurrentUser();
        List<Long> files=checkIn.getFile_id();
       // System.out.println(files.size());
        for (Long file:files
             ) {
            File f = fileRepository.findById(file).orElseThrow(() ->
                    new ResponseException(404, "File Not Found"));
            Auditing auditing= Auditing.builder().user(user).operation("checkin").fileID(f.getId()).date(LocalDate.now()).build();
            if (user.getLogs()==null)
                user.setLogs(List.of(auditing));
            else user.getLogs().add(auditing);
            auditingRepository.save(auditing);
            userRepository.save(user);
            fileRepository.save(f);
        }

    }

    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileService.checkOut(..)) && args(checkOut)", returning = "result")
    public void logCheckout(JoinPoint joinPoint, Object result, CheckInDTO checkOut) throws ResponseException {
        User user = utils.getCurrentUser();
        List<Long> files=checkOut.getFile_id();
        // System.out.println(files.size());
        for (Long file:files
        ) {
            File f = fileRepository.findById(file).orElseThrow(() ->
                    new ResponseException(404, "File Not Found"));
            Auditing auditing= Auditing.builder().user(user).operation("checkout").fileID(f.getId()).date(LocalDate.now()).build();
            if (user.getLogs()==null)
                user.setLogs(List.of(auditing));
            else user.getLogs().add(auditing);

            auditingRepository.save(auditing);
            userRepository.save(user);
            fileRepository.save(f);
        }

    }

}
