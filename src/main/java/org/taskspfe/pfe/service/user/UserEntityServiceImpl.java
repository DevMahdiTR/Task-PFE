package org.taskspfe.pfe.service.user;


import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.dto.user.UserEntityDTOMapper;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.repository.UserEntityRepository;
import org.taskspfe.pfe.utility.CustomResponseEntity;
import org.taskspfe.pfe.utility.CustomResponseList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, UserEntityDTOMapper userEntityDTOMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userEntityDTOMapper = userEntityDTOMapper;
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllAdmins() {
        List<UserEntity> admins = userEntityRepository.fetchAllAdmins();
        List<UserEntityDTO> userEntityDTOList = admins.stream().map(userEntityDTOMapper).toList();
        CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllClients() {
        List<UserEntity> clients = userEntityRepository.fetchAllClients();
        List<UserEntityDTO> userEntityDTOList = clients.stream().map(userEntityDTOMapper).toList();
        CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllTechnicians() {
        List<UserEntity> technicians = userEntityRepository.fetchAllTechnicians();
        List<UserEntityDTO> userEntityDTOList = technicians.stream().map(userEntityDTOMapper).toList();
        CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> enableUser(UUID userId) {
        final UserEntity user = getUserEntityById(userId);
        user.setEnabled(true);
        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(userEntityRepository.save(user));
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> disableUser(UUID userId) {
        final UserEntity user = getUserEntityById(userId);
        user.setEnabled(false);
        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(userEntityRepository.save(user));
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(final UUID userId) {
        final UserEntity user = getUserEntityById(userId);

        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(user);
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<CustomResponseList<UserEntityDTO>> fetchAllUsers(final long pageNumber)
    {
        final Pageable pageable = PageRequest.of((int) pageNumber - 1, 10);


        final List<UserEntityDTO> userEntityFullDTOList = userEntityRepository.fetchAllUsers(pageable).stream().map(userEntityDTOMapper).toList();

        if(userEntityFullDTOList.isEmpty() && pageNumber > 1)
        {
            return fetchAllUsers(1);
        }
        final CustomResponseList<UserEntityDTO> customResponse =
                new CustomResponseList<>(
                        HttpStatus.OK,
                        userEntityFullDTOList,
                        userEntityFullDTOList.size(),
                        userEntityRepository.getTotalUserEntityCount()
                );
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(@NotNull final UserDetails userDetails)
    {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        final UserEntityDTO currentUserDto = userEntityDTOMapper.apply(currentUser);
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,currentUserDto);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);

    }

    public long countClientsByMonth(YearMonth month) {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.plusMonths(1).atDay(1).atStartOfDay();
        return userEntityRepository.countClientsByMonth(start, end);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<Map<String, Long>>> countClientsByYear(int year) {
        Map<String, Long> clientsCountByMonth = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(year, month);
            long count = countClientsByMonth(yearMonth);
            clientsCountByMonth.put(String.format("%d-%02d", year, month), count);
        }
        final CustomResponseEntity<Map<String, Long>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,clientsCountByMonth);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> updateUser(@NotNull UserDetails userDetails,final UserEntity userEntity){
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        currentUser.setFirstName(userEntity.getFirstName());
        currentUser.setLastName(userEntity.getLastName());
        currentUser.setPhoneNumber(userEntity.getPhoneNumber());
        final UserEntityDTO currentUserDto = userEntityDTOMapper.apply(userEntityRepository.save(currentUser));
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,currentUserDto);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }

    @Override
    public boolean isEmailRegistered(final String email)
    {
        return userEntityRepository.isEmailRegistered(email);
    }

    @Override
    public UserEntity saveUser(@NotNull final UserEntity userEntity)
    {
        return userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(final UUID userId)
    {
        return userEntityRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID : %s could not be found.", userId)));
    }

    @Override
    public UserEntity getUserEntityByEmail(@NotNull final String userEmail)
    {
        return userEntityRepository.fetchUserWithEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with email : %s could not be found.", userEmail)));
    }

}
