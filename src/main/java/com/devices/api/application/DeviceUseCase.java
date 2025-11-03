package com.devices.api.application;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceUseCase {
    private final DeviceRepositoryGateway deviceRepositoryGateway;
    private final DeviceMapper mapper;

    public DeviceUseCase(DeviceRepositoryGateway deviceRepositoryGateway, DeviceMapper mapper) {
        this.deviceRepositoryGateway = deviceRepositoryGateway;
        this.mapper = mapper;
    }

    public List<Device> findAll(){
        return this.deviceRepositoryGateway.findAll();
    }

    public DeviceResponseDto create(DeviceRequestDto requestDto){
        Device device = this.mapper.toDomain(requestDto);
        Device deviceSaved = this.deviceRepositoryGateway.save(device);
        return this.mapper.toResponse(deviceSaved);
    }

    public DeviceResponseDto partiallyUpdateDevice(DeviceRequestDto dto, long id){
        Device device = this.deviceRepositoryGateway.findById(id).orElseThrow(
                () -> new RuntimeException("Device not found"));


        device.partiallyUpdate(dto.name(), dto.brand());

        return this.mapper.toResponse(this.deviceRepositoryGateway.save(device));
    }


    public DeviceResponseDto updateDevice(DeviceRequestDto dto, long id) {
        Device existing = deviceRepositoryGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        DeviceState newState = null;
        if (dto.state() != null && !dto.state().isBlank()) {
            try {
                newState = DeviceState.valueOf(dto.state().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid device state: " + dto.state());
            }
        }
        existing.update(dto.name(), dto.brand(), newState);

        Device updated = deviceRepositoryGateway.save(existing);
        return mapper.toResponse(updated);
    }

    public DeviceResponseDto fetchSingleDeviceById(Long id){
        Device fetch = this.deviceRepositoryGateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such device with id "+ id));

        return this.mapper.toResponse(fetch);
    }

    public List<DeviceResponseDto> fetchAllDevices(){
        return this.deviceRepositoryGateway.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<DeviceResponseDto> fetchDevicesByBrand(String brand){
        return this.deviceRepositoryGateway.findByBrand(brand)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }


}
