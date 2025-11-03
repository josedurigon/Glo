package com.devices.api.infra.service;

import com.devices.api.application.DeviceUseCase;
import com.devices.api.domain.entity.Device;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {


    private final DeviceUseCase deviceUseCase;


    public DeviceService(DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }

    public DeviceResponseDto create(DeviceRequestDto dto){
        return this.deviceUseCase.create(dto);
    }

    public DeviceResponseDto partiallyUpdateDevice(DeviceRequestDto dto, long id){
        if(id == 0)
            throw new IllegalStateException("Invalid device ID.");



        return this.deviceUseCase.partiallyUpdateDevice(dto,id);
    }

    public DeviceResponseDto updateDevice(DeviceRequestDto dto, long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Invalid device ID.");
        return deviceUseCase.updateDevice(dto, id);
    }

    public DeviceResponseDto fetchSinggleDevice(Long id){
        return this.deviceUseCase.fetchSingleDeviceById(id);
    }

    public List<DeviceResponseDto> fetchAllDevices(){
        return this.deviceUseCase.fetchAllDevices();
    }

    public List<DeviceResponseDto> fetchDevicesByBrand(String brand){
        return this.deviceUseCase.fetchDevicesByBrand(brand);
    }
}
