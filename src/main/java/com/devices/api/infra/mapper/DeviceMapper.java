package com.devices.api.infra.mapper;

import com.devices.api.domain.entity.Device;
import com.devices.api.infra.persistence.jpa.DeviceEntity;
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

        return new DeviceEntity(
                deviceDomain.getId(),
                deviceDomain.getName(),
                deviceDomain.getBrand(),
                deviceDomain.getState(),
                deviceDomain.getCreationTime()
        );
    }
}
