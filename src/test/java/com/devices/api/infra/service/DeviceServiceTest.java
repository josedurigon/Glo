package com.devices.api.infra.service;

import static org.junit.jupiter.api.Assertions.*;
import com.devices.api.application.DeviceUseCase;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {

    @Mock
    private DeviceUseCase deviceUseCase;

    @InjectMocks
    private DeviceService deviceService;

    private DeviceRequestDto requestDto;
    private DeviceResponseDto responseDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        requestDto = new DeviceRequestDto("iPhone 15", "Apple", "AVAIABLE");
        responseDto = new DeviceResponseDto(
                1L,
                "iPhone 15",
                "Apple",
                "AVAIABLE",
                LocalDateTime.now()
        );
    }

    @Test
    void shouldCreateDeviceSuccessfully() {
        when(deviceUseCase.create(requestDto)).thenReturn(responseDto);

        DeviceResponseDto result = deviceService.create(requestDto);

        assertEquals(responseDto, result);
        verify(deviceUseCase, times(1)).create(requestDto);
    }

    @Test
    void shouldPartiallyUpdateDeviceSuccessfully() {
        long id = 1L;
        when(deviceUseCase.partiallyUpdateDevice(requestDto, id)).thenReturn(responseDto);

        DeviceResponseDto result = deviceService.partiallyUpdateDevice(requestDto, id);

        assertEquals(responseDto, result);
        verify(deviceUseCase, times(1)).partiallyUpdateDevice(requestDto, id);
    }

    @Test
    void shouldThrowWhenPartiallyUpdateWithInvalidId() {
        assertThrows(IllegalStateException.class,
                () -> deviceService.partiallyUpdateDevice(requestDto, 0));
        verifyNoInteractions(deviceUseCase);
    }

    @Test
    void shouldUpdateDeviceSuccessfully() {
        long id = 2L;
        when(deviceUseCase.updateDevice(requestDto, id)).thenReturn(responseDto);

        DeviceResponseDto result = deviceService.updateDevice(requestDto, id);

        assertEquals(responseDto, result);
        verify(deviceUseCase, times(1)).updateDevice(requestDto, id);
    }

    @Test
    void shouldThrowWhenUpdateWithInvalidId() {
        assertThrows(IllegalArgumentException.class,
                () -> deviceService.updateDevice(requestDto, 0));
        verifyNoInteractions(deviceUseCase);
    }

    @Test
    void shouldFetchSingleDevice() {
        Long id = 1L;
        when(deviceUseCase.fetchSingleDeviceById(id)).thenReturn(responseDto);

        DeviceResponseDto result = deviceService.fetchSinggleDevice(id);

        assertEquals(responseDto, result);
        verify(deviceUseCase, times(1)).fetchSingleDeviceById(id);
    }

    @Test
    void shouldFetchAllDevices() {
        List<DeviceResponseDto> devices = List.of(responseDto);
        when(deviceUseCase.fetchAllDevices()).thenReturn(devices);

        List<DeviceResponseDto> result = deviceService.fetchAllDevices();

        assertEquals(1, result.size());
        verify(deviceUseCase, times(1)).fetchAllDevices();
    }

    @Test
    void shouldFetchDevicesByBrand() {
        String brand = "Apple";
        List<DeviceResponseDto> devices = List.of(responseDto);
        when(deviceUseCase.fetchDevicesByBrand(brand)).thenReturn(devices);

        List<DeviceResponseDto> result = deviceService.fetchDevicesByBrand(brand);

        assertEquals(devices, result);
        verify(deviceUseCase, times(1)).fetchDevicesByBrand(brand);
    }

    @Test
    void shouldFetchDevicesByValidState() {
        String state = "AVAIABLE";
        List<DeviceResponseDto> devices = List.of(responseDto);
        when(deviceUseCase.fetchDevicesByState(DeviceState.AVAIABLE)).thenReturn(devices);

        List<DeviceResponseDto> result = deviceService.fetchDevicesByState(state);

        assertEquals(devices, result);
        verify(deviceUseCase, times(1)).fetchDevicesByState(DeviceState.AVAIABLE);
    }

    @Test
    void shouldThrowWhenFetchDevicesByInvalidState() {
        assertThrows(IllegalArgumentException.class,
                () -> deviceService.fetchDevicesByState("INVALID_STATE"));
        verifyNoInteractions(deviceUseCase);
    }

    @Test
    void shouldDeleteDeviceById() {
        Long id = 1L;

        deviceService.deleteDeviceById(id);

        verify(deviceUseCase, times(1)).deleteDevice(id);
    }
}