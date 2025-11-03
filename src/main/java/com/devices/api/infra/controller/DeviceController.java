package com.devices.api.infra.controller;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device")
public class DeviceController {


    private final DeviceRepositoryGateway gateway;
    private final DeviceMapper mapper;

    public DeviceController(DeviceRepositoryGateway gateway, DeviceMapper mapper ) {
        this.gateway = gateway;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public DeviceResponseDto create(@RequestBody DeviceRequestDto dto){
        Device device = this.mapper.toDomain(dto);
        Device deviceSaved = this.gateway.save(device);

        return mapper.toResponse(deviceSaved);

    }
}
