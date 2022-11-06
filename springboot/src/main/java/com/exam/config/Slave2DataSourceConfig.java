package com.exam.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Slave2DataSourceConfig {

    @Value("${spring.datasource.dynamic.datasource.slave_2.url}")
    private String url;

    @Value("${spring.datasource.dynamic.datasource.slave_2.username}")
    private String username;

    @Value("${spring.datasource.dynamic.datasource.slave_2.password}")
    private String password;

    @Value("${spring.datasource.dynamic.datasource.slave_2.driver-class-name}")
    private String driverClassName;

}