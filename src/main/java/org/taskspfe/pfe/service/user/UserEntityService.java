package org.taskspfe.pfe.service.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.utility.CustomResponseEntity;
import org.taskspfe.pfe.utility.CustomResponseList;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserEntityService {

    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllAdmins();
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllClients();
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllTechnicians();

    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(final UUID userId);
    public ResponseEntity<CustomResponseList<UserEntityDTO>> fetchAllUsers(final long pageNumber);
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(final UserDetails userDetails);
    public UserEntity getUserEntityById(final UUID userId);
    public UserEntity getUserEntityByEmail(final String email);
    public boolean isEmailRegistered(final String email);
    public UserEntity saveUser(@NotNull final UserEntity userEntity);

}
