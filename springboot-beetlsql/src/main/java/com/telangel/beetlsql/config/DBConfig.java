package com.telangel.beetlsql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author GavinKing
 * @ClassName: DBConfig
 * @Description:
 * @date 2018/11/18
 */
@Configuration
public class DBConfig {

        @Bean(name = "datasource")
        public DataSource datasource(Environment env) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
            ds.setUsername(env.getProperty("spring.datasource.username"));
            ds.setPassword(env.getProperty("spring.datasource.password"));
            ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            return ds;
        }
}
