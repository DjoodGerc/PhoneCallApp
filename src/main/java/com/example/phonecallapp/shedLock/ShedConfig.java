package com.example.phonecallapp.shedLock;

import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class ShedConfig {
    @Bean
    public LockProvider lockProvider(DataSource dataSource){
        return new JdbcTemplateLockProvider(dataSource);
    }

}
