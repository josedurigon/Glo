package com.devices.api.domain.entity;

import com.devices.api.domain.enun.DeviceState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    private Device device;

    @BeforeEach
    void setUp(){
        device = new Device(1L, "iPhone", "Apple", DeviceState.AVAIABLE, LocalDateTime.now());
    }

    @Test
    void shouldCreateDeviceWithValidFields() {
        assertThat(device.getName()).isEqualTo("iPhone");
        assertThat(device.getBrand()).isEqualTo("Apple");
        assertThat(device.getState()).isEqualTo(DeviceState.AVAIABLE);
        assertThat(device.getCreationTime()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new Device("", "Apple"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Device name cannot be empty.");
    }

    @Test
    void shouldThrowExceptionWhenBrandIsEmpty() {
        assertThatThrownBy(() -> new Device("iPhone", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Device brand cannot be empty.");
    }

    @Test
    void shouldUpdateAllFieldsWhenDeviceIsAvailable() {
        device.update("Galaxy", "Samsung", DeviceState.IN_USE);

        assertThat(device.getName()).isEqualTo("Galaxy");
        assertThat(device.getBrand()).isEqualTo("Samsung");
        assertThat(device.getState()).isEqualTo(DeviceState.IN_USE);
    }

    @Test
    void shouldNotUpdateStateIfNullProvided() {
        device.update("Pixel", "Google", null);

        assertThat(device.getName()).isEqualTo("Pixel");
        assertThat(device.getBrand()).isEqualTo("Google");
        assertThat(device.getState()).isEqualTo(DeviceState.AVAIABLE);
    }

    @Test
    void shouldThrowWhenUpdatingInUseDeviceWithDifferentNameOrBrand() {
        device.setState(DeviceState.IN_USE);

        assertThatThrownBy(() -> device.update("NewName", "Apple", DeviceState.IN_USE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("You shall not update a device that's already in use.");
    }

    @Test
    void shouldPartiallyUpdateNameOrBrand() {
        device.partiallyUpdate("Galaxy", null);

        assertThat(device.getName()).isEqualTo("Galaxy");
        assertThat(device.getBrand()).isEqualTo("Apple");
    }

    @Test
    void shouldThrowWhenPartiallyUpdatingInUseDeviceWithDifferentData() {
        device.setState(DeviceState.IN_USE);

        assertThatThrownBy(() -> device.partiallyUpdate("Galaxy", "Samsung"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("You shall not update a device that's already in use.");
    }

    @Test
    void shouldThrowWhenDeletingInUseDevice() {
        device.setState(DeviceState.IN_USE);

        assertThatThrownBy(device::makeSureDeviceCanBeDeleted)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Device will not be deleted because it's currently in use!");
    }

    @Test
    void shouldAllowDeletionWhenAvailable() {
        device.setState(DeviceState.AVAIABLE);
        assertThatCode(device::makeSureDeviceCanBeDeleted)
                .doesNotThrowAnyException();
    }


    @Test
    void toStringShouldContainAllFields() {
        String result = device.toString();

        assertThat(result)
                .contains("iPhone")
                .contains("Apple")
                .contains("AVAIABLE")
                ;
    }
}