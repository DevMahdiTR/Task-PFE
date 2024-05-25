package org.taskspfe.pfe.controller.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
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

    public UserEntityController(UserEntityService userEntityService)
    {
        this.userEntityService = userEntityService;
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



}
