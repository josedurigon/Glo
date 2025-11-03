package com.devices.api.infra.controller;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;
import com.devices.api.infra.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public DeviceResponseDto create(@RequestBody DeviceRequestDto dto){
        return this.service.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DeviceRequestDto dto) {

       return ResponseEntity.ok().build();
    }

}
