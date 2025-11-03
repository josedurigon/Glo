package com.devices.api.infra.persistence.jpa;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.mapper.DeviceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
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
        DeviceEntity deviceEntity = this.mapper.mapFromDomainToEntity(device);

        DeviceEntity saved = this.deviceRepository.save(deviceEntity);

        return this.mapper.mapFromEntityToDomain(saved);

    }

    @Override
    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id)
                .map(mapper::mapFromEntityToDomain);
    }

    @Override
    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public List<Device> findByBrand(String brand) {
        return this.deviceRepository.findByBrandIgnoreCase(brand)
                .stream()
                .map(mapper::mapFromEntityToDomain)
                .collect(Collectors.toList());

    }
}
