package com.devices.api.gateway;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;

import java.util.List;

public interface DeviceRepositoryGateway {
    List<Device> findAll();
    Device createDevice(String name, String brand, DeviceState state);
}
