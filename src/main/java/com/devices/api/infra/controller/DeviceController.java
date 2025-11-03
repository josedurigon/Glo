package com.devices.api.infra.controller;

import com.devices.api.domain.entity.Device;
import com.devices.api.gateway.DeviceRepositoryGateway;
import com.devices.api.infra.dto.DeviceRequestDto;
import com.devices.api.infra.dto.DeviceResponseDto;
import com.devices.api.infra.mapper.DeviceMapper;
import com.devices.api.infra.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> updatePartially(@PathVariable Long id, @RequestBody DeviceRequestDto dto) {
        this.service.partiallyUpdateDevice(dto, id);
       return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<DeviceResponseDto> update(@PathVariable Long id, @RequestBody DeviceRequestDto dto){
        return ResponseEntity.ok(this.service.updateDevice(dto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDto> fetchSingleDevice(@PathVariable Long id){
        return ResponseEntity.ok(this.service.fetchSingleDevice(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<DeviceResponseDto>> fetchAllDevices(){
        return ResponseEntity.ok(this.service.fetchAllDevices());
    }

    @GetMapping("/fetch/{brand}")
    public ResponseEntity<List<DeviceResponseDto>> fetchDevicesBrand(@PathVariable String brand){
        return ResponseEntity.ok(this.service.fetchDevicesByBrand(brand));
    }

    @GetMapping("/fetch/state/{state}")
    public ResponseEntity<List<DeviceResponseDto>> fetchByDeviceState(@PathVariable String state){
        return ResponseEntity.ok(this.service.fetchDevicesByState(state));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteDeviceById(@PathVariable Long id){
        this.service.deleteDeviceById(id);
        return ResponseEntity.ok().build();
    }


}
