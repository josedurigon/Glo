package com.devices.api.application;


import com.devices.api.infra.dto.*;

import java.util.List;

public interface DeviceUseCaseInterface {
    public DeviceResponseDto partiallyUpdateDevice(DeviceRequestDto dto, long id);
    public DeviceResponseDto create(DeviceRequestDto dto);
    public DeviceResponseDto updateDevice(DeviceRequestDto dto, long id);
    public DeviceResponseDto fetchSingleDevice(Long id);
    public List<DeviceResponseDto> fetchAllDevices();
    public List<DeviceResponseDto> fetchDevicesByBrand(String brand);
    public List<DeviceResponseDto> fetchDevicesByState(String stateString);
    public void deleteDeviceById(Long id);

    }
