package org.fastddd.sample.service.config;

import org.fastddd.spring.transaction.EnhancedDataSourceTransactionManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "org.fastddd.sample.service.infrastructure.mysql")
public class OrderMySqlConfig {


    @Bean
    @ConditionalOnMissingBean(DataSourceTransactionManager.class)
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new EnhancedDataSourceTransactionManager(dataSource);
    }

}
