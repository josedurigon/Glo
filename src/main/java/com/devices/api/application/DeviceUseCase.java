package com.devices.api.application;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;

import java.util.List;

public class DeviceUseCase {
    private final DeviceRepositoryGateway deviceRepositoryGateway;

    public DeviceUseCase(DeviceRepositoryGateway deviceRepositoryGateway) {
        this.deviceRepositoryGateway = deviceRepositoryGateway;
    }

    public List<Device> findAll(){
        return this.deviceRepositoryGateway.findAll();
    }

    //TODO: implementar o resto dos casos de uso.
}
