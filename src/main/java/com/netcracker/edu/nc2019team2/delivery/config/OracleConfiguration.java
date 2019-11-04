package com.netcracker.edu.nc2019team2.delivery.config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties(OracleProperties.class)
public class OracleConfiguration {

    @Bean
    DataSource dataSource(OracleProperties oracleProperties) throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(oracleProperties.getUsername());
        dataSource.setPassword(password);
        dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }
}
