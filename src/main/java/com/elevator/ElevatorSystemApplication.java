package com.elevator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import javax.annotation.PostConstruct;

@SpringBootApplication
public class ElevatorSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(ElevatorSystemApplication.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void logEnvVariables() {
        logger.info("✅ Railway Environment Variables Loaded:");
        logger.info("spring.datasource.url: {}", env.getProperty("spring.datasource.url"));
        logger.info("spring.datasource.username: {}", env.getProperty("spring.datasource.username"));
        logger.info("MYSQLHOST: {}", env.getProperty("MYSQLHOST"));
        logger.info("MYSQLPORT: {}", env.getProperty("MYSQLPORT"));
        logger.info("MYSQLDATABASE: {}", env.getProperty("MYSQLDATABASE"));
        logger.info("MYSQLUSER: {}", env.getProperty("MYSQLUSER"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ElevatorSystemApplication.class, args);
    }
}
