package com.devices.api.application;

import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeviceUseCaseTest {

    @Mock
    private DeviceRepositoryGateway gateway;

    @Mock
    private DeviceMapper mapper;

    @InjectMocks
    private DeviceUseCase useCase;

    private Device device;
    private DeviceRequestDto requestDto;
    private DeviceResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        device = new Device(1L, "iPhone", "Apple", DeviceState.AVAIABLE, LocalDateTime.now());
        requestDto = new DeviceRequestDto("iPhone", "Apple", "AVAIABLE");
        responseDto = new DeviceResponseDto(1L, "iPhone", "Apple", "AVAIABLE", LocalDateTime.now());
    }

    @Test
    void shouldCreateDeviceSuccessfully() {
        when(mapper.toDomain(requestDto)).thenReturn(device);
        when(gateway.save(device)).thenReturn(device);
        when(mapper.toResponse(device)).thenReturn(responseDto);

        DeviceResponseDto result = useCase.create(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("iPhone");
        verify(gateway, times(1)).save(device);
    }

    @Test
    void shouldPartiallyUpdateDevice() {
        when(gateway.findById(1L)).thenReturn(Optional.of(device));
        when(gateway.save(any(Device.class))).thenReturn(device);
        when(mapper.toResponse(any(Device.class))).thenReturn(responseDto);

        DeviceRequestDto updateDto = new DeviceRequestDto("iPhone 15", "Apple", null);
        DeviceResponseDto result = useCase.partiallyUpdateDevice(updateDto, 1L);

        assertThat(result).isNotNull();
        verify(gateway).save(device);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentDevice() {
        when(gateway.findById(1L)).thenReturn(Optional.empty());
        DeviceRequestDto updateDto = new DeviceRequestDto("iPhone", "Apple", "AVAIABLE");

        assertThatThrownBy(() -> useCase.partiallyUpdateDevice(updateDto, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Device not found");
    }

    @Test
    void shouldUpdateDeviceFully() {
        when(gateway.findById(1L)).thenReturn(Optional.of(device));
        when(gateway.save(any(Device.class))).thenReturn(device);
        when(mapper.toResponse(any(Device.class))).thenReturn(responseDto);

        DeviceRequestDto dto = new DeviceRequestDto("Galaxy", "Samsung", "IN_USE");

        DeviceResponseDto result = useCase.updateDevice(dto, 1L);

        assertThat(result).isNotNull();
        verify(gateway).save(device);
    }

    @Test
    void shouldFetchSingleDeviceById() {
        when(gateway.findById(1L)).thenReturn(Optional.of(device));
        when(mapper.toResponse(device)).thenReturn(responseDto);

        DeviceResponseDto result = useCase.fetchSingleDeviceById(1L);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("iPhone");
        verify(gateway).findById(1L);
    }

    @Test
    void shouldFetchDevicesByBrand() {
        when(gateway.findByBrand("Apple")).thenReturn(List.of(device));
        when(mapper.toResponse(device)).thenReturn(responseDto);

        List<DeviceResponseDto> result = useCase.fetchDevicesByBrand("Apple");

        assertThat(result).hasSize(1);
        verify(gateway).findByBrand("Apple");
    }

    @Test
    void shouldDeleteDeviceWhenAvailable() {
        Device available = new Device(1L, "iPhone", "Apple", DeviceState.AVAIABLE, LocalDateTime.now());
        when(gateway.findById(1L)).thenReturn(Optional.of(available));

        useCase.deleteDevice(1L);

        verify(gateway).delete(1L);
    }

    @Test
    void shouldThrowWhenDeletingInUseDevice() {
        Device inUse = new Device(1L, "iPhone", "Apple", DeviceState.IN_USE, LocalDateTime.now());
        when(gateway.findById(1L)).thenReturn(Optional.of(inUse));

        assertThatThrownBy(() -> useCase.deleteDevice(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Device will not be deleted because it's currently in use!");
    }
}