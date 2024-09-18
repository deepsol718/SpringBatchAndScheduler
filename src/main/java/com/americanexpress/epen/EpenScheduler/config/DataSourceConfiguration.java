package com.americanexpress.epen.EpenScheduler.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

//    spring.pg-datasource.driver-class-name=org.postgresql.Driver
//    spring.pg-datasource.jdbc-url=jdbc:postgresql://localhost:5432/ependb
//    spring.pg-datasource.username=postgres
//    spring.pg-datasource.password=1234

    JdbcTemplate jdbcTemplate = null;

    @Value("${spring.pg-datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.pg-datasource.jdbc-url}")
    private String dbUrl;
    @Value("${spring.pg-datasource.username}")
    private String dbUserName;
    @Value("${spring.pg-datasource.password}")
    private String dbPassword;

    @Bean
    @Primary
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setSchema("epen");
        return dataSource;
    }

    @Bean
    @Primary
    public JdbcTemplate createJdbcTemplate(@Qualifier("dataSource") DataSource dataSource){
        jdbcTemplate =  new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }

    @Bean(name = "t325DataSource")
    public DataSource t325DataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        dataSource.setSchema("t325");
        return dataSource;
    }

//    @Bean(name = "quartzDataSource")
//    @QuartzDataSource
//    public DataSource quartzDataSource(){
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setJdbcUrl(dbUrl);
//        dataSource.setUsername(dbUserName);
//        dataSource.setPassword(dbPassword);
//        dataSource.setSchema("epen_qrtz");
//        return dataSource;
//    }

}
