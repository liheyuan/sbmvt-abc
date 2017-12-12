package com.coder4.sbmvt.abc.server.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * @author coder4
 */
@Configuration
public class DBConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "sbmvtAbc.datasource")
    public DataSource sbmvtAbcDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConditionalOnBean(name = "sbmvtAbcDatasource")
    public NamedParameterJdbcTemplate sbmvtAbcTemplate(
            @Qualifier("sbmvtAbcDatasource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
