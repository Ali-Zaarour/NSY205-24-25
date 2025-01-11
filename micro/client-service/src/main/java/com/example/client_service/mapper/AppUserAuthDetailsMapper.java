package com.example.client_service.mapper;


import com.example.client_service.dto.AppUserDTO;
import com.example.client_service.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserAuthDetailsMapper {

    AppUserAuthDetailsMapper MAPPER = org.mapstruct.factory.Mappers.getMapper(AppUserAuthDetailsMapper.class);

    @Mapping(target = "organizationSide", source = "organizationSide")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    AppUserDTO toAppUserDTO(AppUser appUser);

}
