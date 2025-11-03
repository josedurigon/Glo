package com.devices.api.infra.mapper;

import com.devices.api.domain.entity.Device;
import com.devices.api.infra.persistence.jpa.DeviceEntity;

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
}
