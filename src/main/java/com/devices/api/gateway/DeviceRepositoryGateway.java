package com.devices.api.gateway;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DeviceRepositoryGateway {
    List<Device> findAll();
    Device save(Device device); //save can be used both for update and create
    Optional<Device> findById(Long id);
    void delete (Long id);

}
