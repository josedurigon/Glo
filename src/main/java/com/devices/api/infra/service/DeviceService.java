package com.devices.api.infra.service;

import com.devices.api.application.DeviceUseCase;
import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    public List<DeviceResponseDto> fetchDevicesByState(String stateString){
        DeviceState state = null;
        try {
            state = DeviceState.valueOf(stateString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid device state: " + stateString);
        }
        return this.deviceUseCase.fetchDevicesByState(state);
    }

    public void deleteDeviceById(Long id){
        this.deviceUseCase.deleteDevice(id);
    }

}
