package com.devices.api.infra.mapper;


import com.devices.api.domain.entity.Device;
import com.devices.api.domain.enun.DeviceState;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.persistence.jpa.DeviceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


class DeviceMapperTest {


    private DeviceMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new DeviceMapper();
    }

    @Test
    void shouldMapEntityToDomain() {
        LocalDateTime now = LocalDateTime.now();
        DeviceEntity entity = DeviceEntity.builder()
                .id(1L)
                .name("iPhone 15")
                .brand("Apple")
                .state(DeviceState.AVAIABLE)
                .creationTime(now)
                .build();

        Device result = mapper.mapFromEntityToDomain(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getBrand(), result.getBrand());
        assertEquals(entity.getState(), result.getState());
        assertEquals(entity.getCreationTime(), result.getCreationTime());
    }

    @Test
    void shouldThrowWhenEntityIsNull() {
        assertThrows(RuntimeException.class, () -> mapper.mapFromEntityToDomain(null));
    }

    @Test
    void shouldMapDomainToEntity() {
        LocalDateTime now = LocalDateTime.now();
        Device domain = new Device(1L, "iPhone 15", "Apple", DeviceState.AVAIABLE, now);

        DeviceEntity entity = mapper.mapFromDomainToEntity(domain);

        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getBrand(), entity.getBrand());
        assertEquals(domain.getState(), entity.getState());
        assertEquals(domain.getCreationTime(), entity.getCreationTime());
    }

    @Test
    void shouldThrowWhenDomainIsNull() {
        assertThrows(RuntimeException.class, () -> mapper.mapFromDomainToEntity(null));
    }

    @Test
    void shouldMapRequestDtoToDomain() {
        DeviceRequestDto dto = new DeviceRequestDto("iPhone 15", "Apple", "AVAIABLE");

        Device domain = mapper.toDomain(dto);

        assertEquals(dto.name(), domain.getName());
        assertEquals(dto.brand(), domain.getBrand());
    }

    @Test
    void shouldThrowWhenRequestDtoIsNull() {
        assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(null));
    }

    @Test
    void shouldMapDomainToResponseDto() {
        LocalDateTime now = LocalDateTime.now();
        Device device = new Device(1L, "iPhone 15", "Apple", DeviceState.AVAIABLE, now);

        DeviceResponseDto response = mapper.toResponse(device);

        assertEquals(device.getId(), response.id());
        assertEquals(device.getName(), response.name());
        assertEquals(device.getBrand(), response.brand());
        assertEquals(device.getState().name(), response.state());
        assertEquals(device.getCreationTime(), response.creationTime());
    }

    @Test
    void shouldThrowWhenDeviceIsNull() {
        assertThrows(IllegalArgumentException.class, () -> mapper.toResponse(null));
    }
}