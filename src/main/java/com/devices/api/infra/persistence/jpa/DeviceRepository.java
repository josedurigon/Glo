package com.devices.api.infra.persistence.jpa;

import com.devices.api.domain.enun.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    List<DeviceEntity> findByBrandIgnoreCase(String brand);
    List<DeviceEntity> findByState(DeviceState state);
}
