package com.devices.api.domain.entity;

import com.devices.api.domain.enun.DeviceState;

import java.time.LocalDateTime;
import java.util.Objects;

public class Device {
    private long id;
    private String name;
    private String brand;
    private DeviceState state;
    private final LocalDateTime creationTime;


    public Device(String name, String brand, LocalDateTime creationTime) {
        this.name = name;
        this.brand = brand;
        this.state = DeviceState.AVAIABLE;
        this.creationTime = LocalDateTime.now();
        validate(name, brand, state, creationTime);
    }


    public Device(Long id, String name, String brand, DeviceState state, LocalDateTime creationTime) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
        validate(name, brand, state, creationTime);
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

    private void update(String newName, String newBrand, DeviceState newState){
        if (this.state == DeviceState.IN_USE){

            if (!Objects.equals(newName, this.name) || !Objects.equals(newBrand, this.brand)){
                throw new IllegalStateException("You shall not update a device that's already in use.");
            }
        }


        if (this.name != null && !newName.isBlank()){
            this.name = newName;
        }

        if (this.brand != null && !newBrand.isBlank()){
            this.brand = newBrand;
        }

        if (newState != null)
            this.state = newState;
    }


    public void makeSureDeviceCanBeDeleted(){
        if (this.state == DeviceState.IN_USE)
            throw new IllegalStateException("Device will not be deleted because it's currently in use!");
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }


    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", state=" + state +
                ", creationTime=" + creationTime +
                '}';
    }
}
