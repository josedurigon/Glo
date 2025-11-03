package com.devices.api.infra.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.infra.mapper.DeviceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
class DeviceRepositoryJpaGatewayTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceMapper mapper;

    @InjectMocks
    private DeviceRepositoryJpaGateway gateway;

    private Device deviceDomain;
    private DeviceEntity deviceEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deviceDomain = new Device(1L, "iPhone", "Apple", DeviceState.AVAIABLE, LocalDateTime.now());
        deviceEntity = new DeviceEntity(1L, "iPhone", "Apple", DeviceState.AVAIABLE, LocalDateTime.now());
    }

    @Test
    void shouldFindAllDevices() {
        when(deviceRepository.findAll()).thenReturn(List.of(deviceEntity));
        when(mapper.mapFromEntityToDomain(deviceEntity)).thenReturn(deviceDomain);

        List<Device> result = gateway.findAll();

        assertThat(result).hasSize(1);
        verify(deviceRepository).findAll();
        verify(mapper).mapFromEntityToDomain(deviceEntity);
    }

    @Test
    void shouldSaveDeviceSuccessfully() {
        when(mapper.mapFromDomainToEntity(deviceDomain)).thenReturn(deviceEntity);
        when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
        when(mapper.mapFromEntityToDomain(deviceEntity)).thenReturn(deviceDomain);

        Device result = gateway.save(deviceDomain);

        assertThat(result).isEqualTo(deviceDomain);
        verify(mapper).mapFromDomainToEntity(deviceDomain);
        verify(deviceRepository).save(deviceEntity);
        verify(mapper).mapFromEntityToDomain(deviceEntity);
    }

    @Test
    void shouldFindDeviceById() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(deviceEntity));
        when(mapper.mapFromEntityToDomain(deviceEntity)).thenReturn(deviceDomain);

        Optional<Device> result = gateway.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("iPhone");
        verify(deviceRepository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenDeviceNotFound() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Device> result = gateway.findById(1L);

        assertThat(result).isEmpty();
        verify(deviceRepository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldDeleteDeviceById() {
        gateway.delete(1L);

        verify(deviceRepository).deleteById(1L);
    }

    @Test
    void shouldFindByBrandIgnoringCase() {
        when(deviceRepository.findByBrandIgnoreCase("Apple")).thenReturn(List.of(deviceEntity));
        when(mapper.mapFromEntityToDomain(deviceEntity)).thenReturn(deviceDomain);

        List<Device> result = gateway.findByBrand("Apple");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrand()).isEqualTo("Apple");
        verify(deviceRepository).findByBrandIgnoreCase("Apple");
    }

    @Test
    void shouldFindByState() {
        when(deviceRepository.findByState(DeviceState.AVAIABLE)).thenReturn(List.of(deviceEntity));
        when(mapper.mapFromEntityToDomain(deviceEntity)).thenReturn(deviceDomain);

        List<Device> result = gateway.findByState(DeviceState.AVAIABLE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getState()).isEqualTo(DeviceState.AVAIABLE);
        verify(deviceRepository).findByState(DeviceState.AVAIABLE);
    }
}