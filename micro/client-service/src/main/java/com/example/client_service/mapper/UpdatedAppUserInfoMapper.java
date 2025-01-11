package com.example.client_service.mapper;


import com.example.client_service.dto.UpdatedAppUserInfoDTO;
import com.example.client_service.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdatedAppUserInfoMapper {

    UpdatedAppUserInfoMapper MAPPER = org.mapstruct.factory.Mappers.getMapper( UpdatedAppUserInfoMapper.class );

    @Mapping(target = "organizationSide", source = "organizationSide")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    UpdatedAppUserInfoDTO toUpdatedAppUserInfoDTO(AppUser appUser);

}
