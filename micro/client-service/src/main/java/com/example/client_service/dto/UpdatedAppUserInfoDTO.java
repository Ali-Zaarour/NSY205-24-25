package com.example.client_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedAppUserInfoDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("organizationSide")
    private OrganizationSideDTO organizationSide;
}
