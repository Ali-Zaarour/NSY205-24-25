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
public class OrganizationSideDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("description")
    private String description;
}
