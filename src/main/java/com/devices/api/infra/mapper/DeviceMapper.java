package com.devices.api.infra.mapper;

import com.devices.api.domain.entity.Device;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.persistence.jpa.DeviceEntity;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    private DeviceMapper(){}
    public Device mapFromEntityToDomain(DeviceEntity deviceEntity){
        if (deviceEntity == null) throw new RuntimeException("Device entity is null here");
        return new Device(
                deviceEntity.getId(),
                deviceEntity.getName(),
                deviceEntity.getBrand(),
                deviceEntity.getState(),
                deviceEntity.getCreationTime()
        );
    }

    public DeviceEntity mapFromDomainToEntity(Device deviceDomain){
        if(deviceDomain == null) throw new RuntimeException("Device domain is null right here");



        return DeviceEntity.builder()
                .id(deviceDomain.getId() != null && deviceDomain.getId() > 0 ? deviceDomain.getId() : null)
                .name(deviceDomain.getName())
                .brand(deviceDomain.getBrand())
                .state(deviceDomain.getState())
                .creationTime(deviceDomain.getCreationTime())
                .build();

    }
    public Device toDomain(DeviceRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("DeviceRequestDto cannot be null");

        return new Device(dto.name(), dto.brand());
    }

    public DeviceResponseDto toResponse(Device device) {
        if (device == null)
            throw new IllegalArgumentException("Device cannot be null");


        return new DeviceResponseDto(
                device.getId(),
                device.getName(),
                device.getBrand(),
                device.getState().name(),
                device.getCreationTime()
        );
    }
}
