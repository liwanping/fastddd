package org.fastddd.sample.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "org.fastddd.sample.service.infrastructure.mysql")
public class OrderMySqlConfig {



}
