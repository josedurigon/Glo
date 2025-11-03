package com.devices.api.application;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;

import java.util.List;

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




}
