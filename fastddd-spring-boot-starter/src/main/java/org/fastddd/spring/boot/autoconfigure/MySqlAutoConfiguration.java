package org.fastddd.spring.boot.autoconfigure;

import com.mysql.cj.jdbc.Driver;
import org.fastddd.spring.transaction.EnhancedDataSourceTransactionManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MySqlAutoConfiguration {

    @Bean
    @ConditionalOnClass(Driver.class)
    @ConditionalOnMissingBean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new EnhancedDataSourceTransactionManager(dataSource);
    }

}
