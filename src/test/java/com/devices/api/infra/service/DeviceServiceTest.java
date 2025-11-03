package com.devices.api.infra.service;


import com.devices.api.application.exception.UseCaseException;
import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.domain.exception.DomainException;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class DeviceServiceTest {

    @Mock
    private DeviceMapper mapper;
    @Mock
    private DeviceRepositoryGateway repository;

    @InjectMocks
    private DeviceService service;

    private Device device;
    private DeviceRequestDto request;
    private DeviceResponseDto response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        device = new Device(1L, "iPhone 15", "Apple", DeviceState.AVAIABLE, LocalDateTime.now());
        request = new DeviceRequestDto("iPhone 15", "Apple", "AVAIABLE");
        response = new DeviceResponseDto(1L, "iPhone 15", "Apple", "AVAIABLE", LocalDateTime.now());
    }

    @Test
    void shouldCreateDeviceSuccessfully() {
        when(mapper.toDomain(request)).thenReturn(device);
        when(repository.save(device)).thenReturn(device);
        when(mapper.toResponse(device)).thenReturn(response);

        DeviceResponseDto result = service.create(request);

        assertEquals(response, result);
        verify(mapper).toDomain(request);
        verify(repository).save(device);
        verify(mapper).toResponse(device);
    }

    @Test
    void shouldPartiallyUpdateDeviceSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        when(repository.save(device)).thenReturn(device);
        when(mapper.toResponse(device)).thenReturn(response);

        DeviceResponseDto result = service.partiallyUpdateDevice(request, 1L);

        assertEquals(response, result);
        verify(repository).findById(1L);
        verify(repository).save(device);
    }

    @Test
    void shouldThrowWhenPartiallyUpdateDeviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.partiallyUpdateDevice(request, 1L));
    }

    @Test
    void shouldThrowWhenInvalidStateInUpdateDevice() {
        DeviceRequestDto invalid = new DeviceRequestDto("iPhone", "Apple", "INVALID");
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        assertThrows(UseCaseException.class, () -> service.updateDevice(invalid, 1L));
    }

    @Test
    void shouldUpdateDeviceSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        when(repository.save(device)).thenReturn(device);
        when(mapper.toResponse(device)).thenReturn(response);

        DeviceResponseDto result = service.updateDevice(request, 1L);

        assertEquals(response, result);
        verify(repository).save(device);
        verify(mapper).toResponse(device);
    }

    @Test
    void shouldFetchSingleDeviceSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        when(mapper.toResponse(device)).thenReturn(response);

        DeviceResponseDto result = service.fetchSingleDevice(1L);

        assertEquals(response, result);
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowWhenDeviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UseCaseException.class, () -> service.fetchSingleDevice(1L));
    }

    @Test
    void shouldFetchAllDevices() {
        when(repository.findAll()).thenReturn(List.of(device));
        when(mapper.toResponse(device)).thenReturn(response);

        List<DeviceResponseDto> result = service.fetchAllDevices();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void shouldFetchDevicesByBrand() {
        when(repository.findByBrand("Apple")).thenReturn(List.of(device));
        when(mapper.toResponse(device)).thenReturn(response);

        List<DeviceResponseDto> result = service.fetchDevicesByBrand("Apple");

        assertEquals(1, result.size());
        verify(repository).findByBrand("Apple");
    }

    @Test
    void shouldFetchDevicesByState() {
        when(repository.findByState(DeviceState.AVAIABLE)).thenReturn(List.of(device));
        when(mapper.toResponse(device)).thenReturn(response);

        List<DeviceResponseDto> result = service.fetchDevicesByState("AVAIABLE");

        assertEquals(1, result.size());
        verify(repository).findByState(DeviceState.AVAIABLE);
    }

    @Test
    void shouldThrowWhenInvalidStateInFetchDevicesByState() {
        assertThrows(UseCaseException.class, () -> service.fetchDevicesByState("INVALID_STATE"));
    }

    @Test
    void shouldDeleteDeviceById() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        doNothing().when(repository).delete(device.getId());

        service.deleteDeviceById(1L);

        verify(repository).delete(1L);
    }

    @Test
    void shouldThrowWhenDeleteDeviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.deleteDeviceById(1L));
    }

    @Test
    void shouldPropagateDomainExceptionWhenDeleting() {
        Device deviceInUse = mock(Device.class);
        when(repository.findById(1L)).thenReturn(Optional.of(deviceInUse));
        doThrow(new DomainException("Device in use")).when(deviceInUse).makeSureDeviceCanBeDeleted();

        assertThrows(DomainException.class, () -> service.deleteDeviceById(1L));
    }
}