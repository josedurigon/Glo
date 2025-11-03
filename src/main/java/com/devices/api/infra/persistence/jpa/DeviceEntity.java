package com.devices.api.infra.persistence.jpa;


import com.devices.api.domain.enun.DeviceState;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class DeviceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "brand", nullable = false, length = 40)
    private String brand;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceState state;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private LocalDateTime creationTime;


}
