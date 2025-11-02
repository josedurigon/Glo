package com.devices.api.domain.entity;

import com.devices.api.domain.enun.DeviceState;

import java.time.LocalDateTime;

public class Device {
    private long id;
    private String name;
    private String brand;
    private DeviceState state;
    private final LocalDateTime creationTime;


    public Device(long id, String name, String brand, DeviceState state, LocalDateTime creationTime) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

    private void validate(String name, String brand, DeviceState state, LocalDateTime creationTime) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Device name cannot be empty.");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Device brand cannot be empty.");
        }
        if (state == null) {
            throw new IllegalArgumentException("Device state cannot be null.");
        }
        if (creationTime == null) {
            throw new IllegalArgumentException("Creation time cannot be null.");
        }

    }

}
