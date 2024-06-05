package org.taskspfe.pfe.controller.user;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.service.email.EmailSenderService;
import org.taskspfe.pfe.service.user.UserEntityService;
import org.taskspfe.pfe.utility.CustomResponseEntity;
import org.taskspfe.pfe.utility.CustomResponseList;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RequestMapping("api/v1/users")
@RestController
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final EmailSenderService emailSenderService;

    public UserEntityController(UserEntityService userEntityService, EmailSenderService emailSenderService)
    {
        this.userEntityService = userEntityService;
        this.emailSenderService = emailSenderService;
    }


    @GetMapping("/admin/get/id/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(@PathVariable("userId") final UUID userId) {
        return userEntityService.fetchUserById(userId);
    }

    @GetMapping("/admin/get/all/users")
    public ResponseEntity<CustomResponseList<UserEntityDTO>> fetchAllUsers(
            @RequestParam(value = "pageNumber", required = true) final long pageNumber
    )
    {
        return  userEntityService.fetchAllUsers(pageNumber);
    }

    @GetMapping("/all/get/current_user")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(@AuthenticationPrincipal UserDetails userDetails)
    {
        return userEntityService.fetchCurrentUser(userDetails);
    }

    @GetMapping("/all/admins")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllAdmins() {
        return userEntityService.fetchAllAdmins();
    }

    @GetMapping("/all/clients")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllClients() {
        return userEntityService.fetchAllClients();
    }

    @PutMapping("/admin/enable/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> enableUser(@PathVariable("userId") final UUID userId) {
        return userEntityService.enableUser(userId);
    }
    @GetMapping("/admin/count/clients/{year}")
    public ResponseEntity<CustomResponseEntity<Map<String, Long>>> countClientsByYear(@PathVariable("year") final int year) {
        return userEntityService.countClientsByYear(year);
    }

    @PutMapping("/admin/disable/{userId}")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> disableUser(@PathVariable("userId") final UUID userId) {
        return userEntityService.disableUser(userId);
    }

    @GetMapping("/all/technicians")
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllTechnicians() {
        return userEntityService.fetchAllTechnicians();
    }
    @PutMapping("/all/update")
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> updateUser(
            @AuthenticationPrincipal @NotNull UserDetails userDetails,
            @RequestBody  final UserEntity userEntity
    ){
        return userEntityService.updateUser(userDetails,userEntity);
    }



    @GetMapping("/all/reclamation")
    public ResponseEntity<CustomResponseEntity<String>> sendMail(
            @RequestParam(name = "subject" , required = true) String subject ,
            @RequestParam(name = "description" , required = true) String description){
        emailSenderService.sendEmail("medmahdidev@gmail.com",subject , emailSenderService.emailTemplateContact(subject,description));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK,"Email sent successfully"));
    }




}
