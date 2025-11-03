package com.devices.api.configuration;

import com.devices.api.application.DeviceUseCase;
import com.devices.api.infra.mapper.DeviceMapper;
import com.devices.api.infra.persistence.jpa.DeviceRepositoryJpaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public DeviceUseCase deviceUseCase(DeviceRepositoryJpaGateway deviceRepositoryJpaGateway, DeviceMapper mapper){
        return new DeviceUseCase(deviceRepositoryJpaGateway, mapper);
    }
}
