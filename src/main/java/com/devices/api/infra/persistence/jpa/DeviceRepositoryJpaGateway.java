package com.devices.api.infra.persistence.jpa;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.mapper.DeviceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeviceRepositoryJpaGateway implements DeviceRepositoryGateway {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper mapper;

    public DeviceRepositoryJpaGateway(DeviceRepository deviceRepository, DeviceMapper mapper) {
        this.deviceRepository = deviceRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Device> findAll() {
        return this.deviceRepository.findAll()
                .stream().map(mapper::mapFromEntityToDomain)
                .collect(Collectors.toList());

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
