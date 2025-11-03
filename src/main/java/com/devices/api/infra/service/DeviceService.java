package com.devices.api.infra.service;

import com.devices.api.application.DeviceUseCase;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeviceService {


    private final DeviceUseCase deviceUseCase;


    public DeviceService( DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }


}
