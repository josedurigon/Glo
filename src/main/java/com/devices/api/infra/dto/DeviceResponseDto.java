package com.devices.api.infra.dto;

import java.time.LocalDateTime;

public record DeviceResponseDto(
        Long id,
        String name,
        String brand,
        String state,
        LocalDateTime creationTime

) {


}
