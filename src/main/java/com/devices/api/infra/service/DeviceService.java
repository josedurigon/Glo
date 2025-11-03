package com.devices.api.infra.service;

import com.devices.api.application.DeviceUseCaseInterface;
import com.devices.api.application.exception.ResourceNotFoundException;
import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.application.exception.UseCaseException;
import com.devices.api.infra.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService implements DeviceUseCaseInterface {


    private final DeviceMapper mapper;
    private final DeviceRepositoryGateway deviceRepositoryGateway;



    public DeviceService(DeviceMapper mapper, DeviceRepositoryGateway gateway) {
        this.mapper = mapper;
        this.deviceRepositoryGateway = gateway;
    }

    @Override
    public DeviceResponseDto create(DeviceRequestDto dto){
        Device device = this.mapper.toDomain(dto);
        Device deviceSaved = this.deviceRepositoryGateway.save(device);
        return this.mapper.toResponse(deviceSaved);
    }

    @Override
    public DeviceResponseDto partiallyUpdateDevice(DeviceRequestDto dto, long id){
        Device device = this.deviceRepositoryGateway.findById(id).orElseThrow(
                () -> new RuntimeException("Device not found"));



        device.partiallyUpdate(dto.name(), dto.brand());


        return this.mapper.toResponse(this.deviceRepositoryGateway.save(device));

    }

    @Override
    public DeviceResponseDto updateDevice(DeviceRequestDto dto, long id) {
        Device existing = deviceRepositoryGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        DeviceState newState = null;
        if (dto.state() != null && !dto.state().isBlank()) {
            try {
                newState = DeviceState.valueOf(dto.state().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new UseCaseException("Invalid device state: " + dto.state());
            }
        }
        existing.update(dto.name(), dto.brand(), newState);

        Device updated = deviceRepositoryGateway.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    public DeviceResponseDto fetchSingleDevice(Long id){
        Device fetch = this.deviceRepositoryGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such device with id "+ id));

        return this.mapper.toResponse(fetch);    }

    @Override
    public List<DeviceResponseDto> fetchAllDevices(){
        return this.deviceRepositoryGateway.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());    }

    @Override
    public List<DeviceResponseDto> fetchDevicesByBrand(String brand){
        return this.deviceRepositoryGateway.findByBrand(brand)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());    }

    @Override
    public List<DeviceResponseDto> fetchDevicesByState(String stateString){
        DeviceState state = null;
        try {
            state = DeviceState.valueOf(stateString.trim().toUpperCase());
            return this.deviceRepositoryGateway.findByState(state)
                    .stream()
                    .map(mapper::toResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new UseCaseException("Invalid device state: " + stateString);
        }
    }

    @Override
    public void deleteDeviceById(Long id){
        Device device = this.deviceRepositoryGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device with id "+ id));

        device.makeSureDeviceCanBeDeleted();

        this.deviceRepositoryGateway.delete(device.getId());    }

}
