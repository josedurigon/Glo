package com.devices.api.infra.persistence.jpa;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;

import java.util.List;
import java.util.Optional;

public class DeviceRepositoryJpaGateway implements DeviceRepositoryGateway {

    private final DeviceRepository deviceRepository;

    public DeviceRepositoryJpaGateway(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<Device> findAll() {
        return this.deviceRepository.findAll();
    }

    @Override
    public Device save(Device device) {
        return null;
    }

    @Override
    public Optional<Device> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
