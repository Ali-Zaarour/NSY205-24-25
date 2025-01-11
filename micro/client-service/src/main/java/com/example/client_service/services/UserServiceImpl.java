package com.example.client_service.services;


import com.example.client_service.dto.AppUserDTO;
import com.example.client_service.dto.UpdatedAppUserInfoDTO;
import com.example.client_service.entity.AppUser;
import com.example.client_service.entity.OrganizationSide;
import com.example.client_service.exception.UserNotFoundException;
import com.example.client_service.exception.config.ExceptionMessage;
import com.example.client_service.mapper.AppUserAuthDetailsMapper;
import com.example.client_service.mapper.UpdatedAppUserInfoMapper;
import com.example.client_service.payload.CreateUserRequest;
import com.example.client_service.payload.UpdateUserRequest;
import com.example.client_service.repositories.AppUserRepository;
import com.example.client_service.repositories.OrganizationSideRepository;
import com.example.client_service.utils.DBParamsName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final OrganizationSideRepository organizationSideRepository;
    private final PasswordEncoder argon2PasswordEncoder;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder argon2PasswordEncoder, OrganizationSideRepository organizationSideRepository) {
        this.appUserRepository = appUserRepository;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
        this.organizationSideRepository = organizationSideRepository;
    }

    @Override
    public Optional<AppUserDTO> createUser(CreateUserRequest createUserRequest) {
        //check if user already exists with the same username provided by the administrator
        var exist = appUserRepository.existsByUsername(createUserRequest.getUsername());
        if (!exist) {
            // create a new client
            var newAppUser = AppUser.builder()
                    .username(createUserRequest.getUsername())
                    .password(argon2PasswordEncoder.encode(createUserRequest.getPassword()))
                    .organizationSide(OrganizationSide.builder().id(createUserRequest.getOrganizationId()).build())
                    .build();
            var createdAppUser = appUserRepository.save(newAppUser);
            return Optional.of(AppUserAuthDetailsMapper.MAPPER.toAppUserDTO(createdAppUser));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UpdatedAppUserInfoDTO> updateUser(UpdateUserRequest updateUserRequest, UUID principalId) {

        // if username/password/organizationId is not null, start the update process
        if (updateUserRequest.getUsername() != null || updateUserRequest.getPassword() != null || updateUserRequest.getOrganizationId() != null) {

            // define Map<String, Object> updates to store the fields to update
            Map<String, Object> updates = new HashMap<>();

            // get user current data, base on the user id provided if it doesn't exist throw an exception not found 404
            if (!appUserRepository.existsById(updateUserRequest.getUserId())) throw new UserNotFoundException();

            // if not null,verifies if the organization id exist,
            // if not throw exception 409, and different from the last one
            if (updateUserRequest.getOrganizationId() != null) {
                var organizationSide = organizationSideRepository.findById(updateUserRequest.getOrganizationId()).orElseThrow(() -> new DataIntegrityViolationException(ExceptionMessage.DATA_INTEGRITY_VIOLATION));
                updates.put(DBParamsName.APP_USER_ORGANIZATION_SIDE, organizationSide);
            }
            // if it's not null, update the username
            if (updateUserRequest.getUsername() != null) {
                //check if this username contains only letters and numbers
                if (!updateUserRequest.getUsername().matches("^[a-zA-Z0-9]*$")) {
                    throw new DataIntegrityViolationException(ExceptionMessage.UNMATCHED_PATTERN);
                }
                updates.put(DBParamsName.APP_USER_USERNAME, updateUserRequest.getUsername());
                updates.put(DBParamsName.APP_USER_UPDATED_AT, System.currentTimeMillis());
            }
            //hash the password if it's not null and save
            if (updateUserRequest.getPassword() != null) {
                updates.put(DBParamsName.APP_USER_PASSWORD, argon2PasswordEncoder.encode(updateUserRequest.getPassword()));
                updates.put(DBParamsName.APP_USER_PSW_LAST_UPDATE, System.currentTimeMillis());
            }

            //save the user data and return the user data
            if (appUserRepository.updateMainInfo(updateUserRequest.getUserId(), updates) > 0) {
                var updatedUserInfo = appUserRepository.findById(updateUserRequest.getUserId());
                if (updatedUserInfo.isEmpty()) throw new UserNotFoundException();
                return Optional.of(UpdatedAppUserInfoMapper.MAPPER.toUpdatedAppUserInfoDTO(updatedUserInfo.get()));
            }


        }
        return Optional.empty();
    }

}
