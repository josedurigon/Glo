package com.devices.api.infra.service;

import com.devices.api.application.DeviceUseCase;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {


    private final DeviceUseCase deviceUseCase;


    public DeviceService(DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }

    public DeviceResponseDto create(DeviceRequestDto dto){
        return this.deviceUseCase.create(dto);
    }
}
