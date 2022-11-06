package com.exam.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Slave1DataSourceConfig {

    @Value("${spring.datasource.dynamic.datasource.slave_1.url}")
    private String url;

    @Value("${spring.datasource.dynamic.datasource.slave_1.username}")
    private String username;

    @Value("${spring.datasource.dynamic.datasource.slave_1.password}")
    private String password;

    @Value("${spring.datasource.dynamic.datasource.slave_1.driver-class-name}")
    private String driverClassName;

}