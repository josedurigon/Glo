package com.devices.api.infra.persistence.jpa;

import com.devices.api.domain.enun.DeviceState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ComponentScan(basePackages = "com.devices.api.infra.persistence.jpa")
class DeviceEntityTest {

    @Autowired
    private DeviceRepository repository;

    @Test
    void shouldPersistAndRetrieveDeviceEntity() {
        DeviceEntity entity = DeviceEntity.builder()
                .name("iPhone 15")
                .brand("Apple")
                .state(DeviceState.AVAIABLE)
                .creationTime(LocalDateTime.now())
                .build();

        DeviceEntity saved = repository.save(entity);
        DeviceEntity found = repository.findById(saved.getId()).orElseThrow();

        assertThat(found.getId()).isNotNull();
        assertThat(found.getName()).isEqualTo("iPhone 15");
        assertThat(found.getBrand()).isEqualTo("Apple");
        assertThat(found.getState()).isEqualTo(DeviceState.AVAIABLE);
        assertThat(found.getCreationTime()).isNotNull();
    }

    @Test
    void shouldSaveEnumAsString() {
        DeviceEntity entity = DeviceEntity.builder()
                .name("Galaxy S24")
                .brand("Samsung")
                .state(DeviceState.IN_USE)
                .creationTime(LocalDateTime.now())
                .build();

        DeviceEntity saved = repository.save(entity);
        DeviceEntity found = repository.findById(saved.getId()).orElseThrow();

        assertThat(found.getState()).isEqualTo(DeviceState.IN_USE);
    }

    @Test
    void shouldNotAllowNullNameOrBrand() {
        DeviceEntity invalid = new DeviceEntity();
        invalid.setState(DeviceState.AVAIABLE);
        invalid.setCreationTime(LocalDateTime.now());

        assertThrows(Exception.class, () -> repository.saveAndFlush(invalid));
    }
}
