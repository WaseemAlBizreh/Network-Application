package com.networkapplication.aspects;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.*;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.Auditing;
import com.networkapplication.models.File;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.AuditingRepository;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import com.networkapplication.services.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class Log {
    private final AuditingRepository auditingRepository;
    private final FileRepository fileRepository;
    private final Utils utils;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

//File logs
    //create
    //TODO:make logCreateFile
    //checkin
    //checkIn
    // success:
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileService.checkIn(..)) && args(checkIn)", returning = "result")
    public void logCheckin(JoinPoint joinPoint, Object result, CheckInDTO checkIn) throws ResponseException {
        User user = utils.getCurrentUser();
        List<Long> files = checkIn.getFile_id();
        for (Long file : files) {
            File f = fileRepository.findById(file).orElseThrow(() -> new ResponseException(404, "File Not Found"));
            Auditing auditing = Auditing.builder()
                    .user(user)
                    .operation("FileCheckIn")
                    .affectedID(f.getId())
                    .date(LocalDate.now())
                    .result("success")
                    .build();
            if (user.getLogs() == null) user.setLogs(List.of(auditing));
            else user.getLogs().add(auditing);
            auditingRepository.save(auditing);
            userRepository.save(user);
        }
    }
    //fail
    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.FileController.checkIn(..)) && args(checkIn)",returning = "result")
    public void logCheckInFault(CheckInDTO checkIn, ResponseEntity<MainDTO> result) {

        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            User user = utils.getCurrentUser();
            List<Long> files = checkIn.getFile_id();
            for (Long file : files) {
                Auditing auditing = Auditing.builder()
                        .user(user)
                        .operation("FileCheckIn")
                        .date(LocalDate.now()).affectedID(file)
                        .result(name2)
                        .build();
                if (user.getLogs() == null) user.setLogs(List.of(auditing));
                else{
                    user.getLogs().add(auditing);
                }
                auditingRepository.save(auditing);
                userRepository.save(user);
            }
        }
    }


    //checkout
    //success
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileService.checkOut(..)) && args(checkOut)", returning = "result")
    public void logCheckout(JoinPoint joinPoint, Object result, CheckInDTO checkOut) throws ResponseException {
        User user = utils.getCurrentUser();
        List<Long> files = checkOut.getFile_id();
        for (Long file : files) {
            File f = fileRepository.findById(file).orElseThrow(() -> new ResponseException(404, "File Not Found"));
            Auditing auditing = Auditing.builder().user(user).operation("FileCheckOut").affectedID(f.getId()).date(LocalDate.now()).result("success").build();
            if (user.getLogs() == null) user.setLogs(List.of(auditing));
            else user.getLogs().add(auditing);
            auditingRepository.save(auditing);
            userRepository.save(user);

        }
    }
    //fail
    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.FileController.checkOut(..)) && args(checkOut)",returning = "result")
    public void logCheckOutFault(CheckInDTO checkOut, ResponseEntity<MainDTO> result) {

        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            User user = utils.getCurrentUser();
            List<Long> files = checkOut.getFile_id();
            for (Long file : files) {
                Auditing auditing = Auditing.builder()
                        .user(user)
                        .operation("FileCheckOut")
                        .date(LocalDate.now()).affectedID(file)
                        .result(name2)
                        .build();
                if (user.getLogs() == null) user.setLogs(List.of(auditing));
                else{
                    user.getLogs().add(auditing);
                }
                auditingRepository.save(auditing);
                userRepository.save(user);
            }
        }
    }


    //update
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileService.updateFile(..)) && args(group_id,file1)", returning = "result")
    public void logUpdateFile(JoinPoint joinPoint, Object result, Long group_id, MultipartFile file1) throws ResponseException {
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(group_id).orElseThrow(() -> new ResponseException(404, "Group Not Found"));
        ;
        List<File> files = group.getFile();
        Long id = null;
        for (File file : files) {
            if (file.getFileName().replace(group_id.toString(), "").equals(file1.getOriginalFilename()))
                id = file.getId();
            break;
        }
        if (id == null) return;
        Auditing auditing = Auditing.builder().user(user).operation("FileUpdate").affectedID(id).date(LocalDate.now()).result("success").build();
        if (user.getLogs() == null) user.setLogs(List.of(auditing));
        else user.getLogs().add(auditing);
        auditingRepository.save(auditing);
        userRepository.save(user);


    }

    //delete
    //success:
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.FileService.deleteFile(..)) && args(groupId,filesID)", returning = "result")
    public void logDeleteFile(JoinPoint joinPoint, Object result, Long groupId, CheckInDTO filesID) throws ResponseException {
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResponseException(404, "Group Not Found"));
        ;
        List<Long> files = filesID.getFile_id();
        for (Long file : files) {
            Auditing auditing = Auditing.builder().user(user).operation("FileDelete").affectedID(file).date(LocalDate.now()).result("success").build();
            if (user.getLogs() == null) user.setLogs(List.of(auditing));
            else user.getLogs().add(auditing);
            auditingRepository.save(auditing);
            userRepository.save(user);
        }
    }
    //fail:
    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.FileController.deleteFile(..)) && args(groupId,filesId)",returning = "result")
    public void logDeleteFileFault(Long groupId, CheckInDTO filesId, ResponseEntity<MainDTO> result) {

        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            User user = utils.getCurrentUser();
            List<Long> files = filesId.getFile_id();
            for (Long file : files) {
                Auditing auditing = Auditing.builder()
                        .user(user)
                        .operation("deleteFile")
                        .date(LocalDate.now()).affectedID(file)
                        .result(name2)
                        .build();
                if (user.getLogs() == null) user.setLogs(List.of(auditing));
                else{
                    user.getLogs().add(auditing);
                }
                auditingRepository.save(auditing);
                userRepository.save(user);
            }
        }
    }



//Group logs
    //create
    //??
    //delete
    //success:
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.GroupService.deleteGroup(..)) && args(id)", returning = "result")
    public void logDeleteGroup(JoinPoint joinPoint, Object result, Long id) throws ResponseException {
        User user = utils.getCurrentUser();
        Auditing auditing = Auditing.builder().user(user).operation("GroupDelete").affectedID(id).date(LocalDate.now()).result("success").build();
        if (user.getLogs() == null) user.setLogs(List.of(auditing));
        else user.getLogs().add(auditing);
        auditingRepository.save(auditing);
        userRepository.save(user);
    }
    //fail:
    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.GroupController.deleteGroup(..)) && args(groupId)",returning = "result")
    public void logDeleteGroupFault(Long groupId, ResponseEntity<MainDTO> result) {
        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            User user = utils.getCurrentUser();
            Auditing auditing = Auditing.builder()
                    .user(user)
                    .operation("GroupDelete")
                    .date(LocalDate.now())
                    .affectedID(groupId)
                    .result(name2)
                    .build();
            if (user.getLogs() == null) user.setLogs(List.of(auditing));
            else{
                user.getLogs().add(auditing);
            }
            auditingRepository.save(auditing);
            userRepository.save(user);
        }
    }
    //addUser
    //success:
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.GroupService.addUser(..)) && args(request)", returning = "result")
    public void logAddUserToGroup(JoinPoint joinPoint, Object result, AddUserToGroupRequest request){
       Optional <User> user1 = userRepository.findById(request.getUser_id());
       if (user1.isPresent()){
           User user=user1.get();
        Auditing auditing = Auditing.builder().user(user).operation("AddUserToGroup").affectedID(request.getGroup_id()).date(LocalDate.now()).result("success").build();
        if (user.getLogs() == null) user.setLogs(List.of(auditing));
        else user.getLogs().add(auditing);
        auditingRepository.save(auditing);
        userRepository.save(user);}
       else {
           Auditing auditing = Auditing.builder().user(null).operation("AddUserToGroup").affectedID(request.getGroup_id()).date(LocalDate.now()).result("success").build();
           auditingRepository.save(auditing);
       }
    }
    //fail:
    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.GroupController.addUser(..)) && args(request)",returning = "result")
    public void logAddUserToGroupFault(AddUserToGroupRequest request, ResponseEntity<MainDTO> result) {
        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            Optional<User> user1 = userRepository.findById(request.getUser_id());
            if (user1.isPresent()){
                User user=user1.get();
                Auditing auditing = Auditing.builder()
                        .user(user)
                        .operation("GroupDelete")
                        .date(LocalDate.now())
                        .affectedID(request.getGroup_id())
                        .result(name2)
                        .build();
                if (user.getLogs() == null) user.setLogs(List.of(auditing));
                else{
                    user.getLogs().add(auditing);
                }
                auditingRepository.save(auditing);
                userRepository.save(user);}
            else {

                Auditing auditing = Auditing.builder()
                        .user(null)
                        .operation("GroupDelete")
                        .date(LocalDate.now())
                        .affectedID(request.getGroup_id())
                        .result(name2)
                        .build();
                auditingRepository.save(auditing);
            }}

    }

    //deleteUser
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.GroupService.deleteUser(..)) && args(deleteDTOUser)", returning = "result")
    public void logDeleteUserFromGroup(JoinPoint joinPoint, Object result, DeleteDTOUser deleteDTOUser) throws ResponseException {

        User user = userRepository.findById(deleteDTOUser.getUserId())
                .orElseThrow(() -> new ResponseException(404, "No User Found"));
        Auditing auditing = Auditing.builder().user(user).operation("DeleteUserFromGroup").affectedID(deleteDTOUser.getGroupId()).date(LocalDate.now()).result("success").build();
        if (user.getLogs() == null) user.setLogs(List.of(auditing));
        else user.getLogs().add(auditing);
        auditingRepository.save(auditing);
        userRepository.save(user);
    }
    //leaveGroup
    @AfterReturning(pointcut = "execution(* com.networkapplication.services.GroupService.leaveGroup(..)) && args(group_id)", returning = "result")
    public void logUserLeaveGroup(JoinPoint joinPoint, Object result, Long group_id) throws ResponseException {
        User user =utils.getCurrentUser();
        Auditing auditing = Auditing.builder().user(user).operation("LeaveGroup").affectedID(group_id).date(LocalDate.now()).result("success").build();
        if (user.getLogs() == null) user.setLogs(List.of(auditing));
        else user.getLogs().add(auditing);
        auditingRepository.save(auditing);
        userRepository.save(user);
    }

   //THROW


    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.FileController.deleteAllFiles(..)) && args(groupId)",returning = "result")
    public void deleteAllFilesInGroupFault(Long groupId, ResponseEntity<MainDTO> result) {

        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            User user = utils.getCurrentUser();
            Auditing auditing = Auditing.builder()
                    .user(user)
                    .operation("deleteFile")
                    .date(LocalDate.now()).affectedID(groupId)
                    .result(name2)
                    .build();
            if (user.getLogs() == null) user.setLogs(List.of(auditing));
            else{
                user.getLogs().add(auditing);
            }
            auditingRepository.save(auditing);
            userRepository.save(user);
        }
    }


    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.AuthController.login(..)) && args(user1)",returning = "result")
    public void logLoginFault(UserDTORequest user, ResponseEntity<MainDTO> result) {
        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);

            Auditing auditing = Auditing.builder()
                    .user(null)
                    .operation("login")
                    .date(LocalDate.now()).affectedID(0L)
                    .result(name2)
                    .build();
            auditingRepository.save(auditing);

        }
    }


    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.AuthController.register(..)) && args(user1)",returning = "result")
    public void logRegisterFault(UserDTORequest user, ResponseEntity<MainDTO> result) {
        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);

            Auditing auditing = Auditing.builder()
                    .user(null)
                    .operation("register")
                    .date(LocalDate.now()).affectedID(0L)
                    .result(name2)
                    .build();
            auditingRepository.save(auditing);

        }
    }


    @AfterReturning(pointcut = "execution(* com.networkapplication.controllers.AuthController.registerAsAdmin(..)) && args(user1)",returning = "result")
    public void logRegisterAsAdmin(AdminRegisterDTO user, ResponseEntity<MainDTO> result) {
        if(!result.getStatusCode().is2xxSuccessful()){
            String name= Objects.requireNonNull(result.getBody()).toString().replace("ErrorDTO","");
            String name2=name.substring(1,name.length()-1);
            Optional<User>  user1=userRepository.findUserByUsername(user.getUsername());
            User user2=null;
            if(user1.isPresent()){
                user2=user1.get();
            }
            Auditing auditing = Auditing.builder()
                    .user(user2)
                    .operation("registerAsAdmin")
                    .date(LocalDate.now()).affectedID(0L)
                    .result(name2)
                    .build();
            auditingRepository.save(auditing);

        }
    }

}



