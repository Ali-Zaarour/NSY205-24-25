package com.example.finance_app.services;

import com.example.finance_app.dto.AppUserDTO;
import com.example.finance_app.dto.UpdatedAppUserInfoDTO;
import com.example.finance_app.entity.AppUser;
import com.example.finance_app.entity.OrganizationSide;
import com.example.finance_app.exception.UserNotFoundException;
import com.example.finance_app.exception.config.ExceptionMessage;
import com.example.finance_app.mapper.AppUserAuthDetailsMapper;
import com.example.finance_app.mapper.UpdatedAppUserInfoMapper;
import com.example.finance_app.payload.CreateUserRequest;
import com.example.finance_app.payload.UpdateUserRequest;
import com.example.finance_app.repositories.AppUserRepository;
import com.example.finance_app.repositories.OrganizationSideRepository;
import com.example.finance_app.utils.DBParamsName;
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
    public Optional<AppUserDTO> createUser(CreateUserRequest createUserRequest, UUID principalId) {
        //check if user already exists with the same username provided by the administrator
        var exist = appUserRepository.existsByUsername(createUserRequest.getUsername());
        if (!exist) {
            // create a new client
            var newAppUser = AppUser.builder()
                    .username(createUserRequest.getUsername())
                    .password(argon2PasswordEncoder.encode(createUserRequest.getPassword()))
                    .organizationSide(OrganizationSide.builder().id(createUserRequest.getOrganizationId()).build())
                    .createdBy(AppUser.builder().id(principalId).build())
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

            // if not null,verifies if the organization id exist , if not throw exception 409, and not the same as the last one
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
                updates.put(DBParamsName.APP_USER_UPDATED_BY, AppUser.builder().id(principalId).build());
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
