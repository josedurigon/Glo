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



}
