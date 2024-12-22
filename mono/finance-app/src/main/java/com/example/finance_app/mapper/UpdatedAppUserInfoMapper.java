package com.example.finance_app.mapper;



import com.example.finance_app.dto.UpdatedAppUserInfoDTO;
import com.example.finance_app.entity.AppUser;
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
