package com.exam.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DateSourceComponent {

    @Resource
    private MasterDataSourceConfig masterDataSourceConfig;

    @Resource
    private Slave1DataSourceConfig slave1DataSourceConfig;

    @Resource
    private Slave2DataSourceConfig slave2DataSourceConfig;

    @Bean(name = "master")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(masterDataSourceConfig.getUrl());
        dataSource.setUsername(masterDataSourceConfig.getUsername());
        dataSource.setPassword(masterDataSourceConfig.getPassword());
        dataSource.setDriverClassName(masterDataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Bean(name = "slave_1")
    public DataSource slave1DataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slave1DataSourceConfig.getUrl());
        dataSource.setUsername(slave1DataSourceConfig.getUsername());
        dataSource.setPassword(slave1DataSourceConfig.getPassword());
        dataSource.setDriverClassName(slave1DataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Bean(name = "slave_2")
    public DataSource slave2DataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slave2DataSourceConfig.getUrl());
        dataSource.setUsername(slave2DataSourceConfig.getUsername());
        dataSource.setPassword(slave2DataSourceConfig.getPassword());
        dataSource.setDriverClassName(slave2DataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Autowired
    @Qualifier("master")
    private DataSource masterDateSource;


    @Autowired
    @Qualifier("slave_1")
    private DataSource slave1DataSource;

    @Autowired
    @Qualifier("slave_2")
    private DataSource slava2DataSource;


    @Primary
    @DependsOn({"master", "slave"}) // 解决数据库循环和依赖的问题
    @Bean(name = "multiDataSource")
    public MultiRouteDataSource exampleRouteDateSource() {
        MultiRouteDataSource multiRouteDataSource = new MultiRouteDataSource();
        Map<Object, Object> targetDateSource = new HashMap<>();
        targetDateSource.put("master", masterDateSource);
        targetDateSource.put("slave_1", slave1DataSource);
        targetDateSource.put("slave_2", slava2DataSource);
        multiRouteDataSource.setTargetDataSources(targetDateSource);
        multiRouteDataSource.setDefaultTargetDataSource(masterDateSource);
        return multiRouteDataSource;
    }

}
