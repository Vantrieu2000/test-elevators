package com.elevator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import javax.annotation.PostConstruct;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ElevatorSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(ElevatorSystemApplication.class);
    
    static {
        // Load .env file
        Dotenv dotenv = Dotenv.configure().load();
        
        // Set system properties from .env
        dotenv.entries().forEach(e -> {
            if (System.getProperty(e.getKey()) == null) {
                System.setProperty(e.getKey(), e.getValue());
            }
        });
        
        // Log loaded environment variables
        logger.info("Loaded environment variables from .env file");
        logger.info("MYSQL_URL: {}", System.getProperty("MYSQL_URL"));
        logger.info("MYSQLUSER: {}", System.getProperty("MYSQLUSER"));
    }
    
    @Autowired
    private Environment env;
    
    @PostConstruct
    public void logEnvVariables() {
        logger.info("Database URL: {}", env.getProperty("spring.datasource.url"));
        logger.info("Database Username: {}", env.getProperty("spring.datasource.username"));
        logger.info("Database Password length: {}", 
            env.getProperty("spring.datasource.password") != null ? 
            env.getProperty("spring.datasource.password").length() : 0);
        
        // Log các biến môi trường Railway
        logger.info("MYSQLHOST: {}", env.getProperty("MYSQLHOST"));
        logger.info("MYSQLPORT: {}", env.getProperty("MYSQLPORT"));
        logger.info("MYSQLDATABASE: {}", env.getProperty("MYSQLDATABASE"));
        logger.info("MYSQLUSER: {}", env.getProperty("MYSQLUSER"));
        // Không log mật khẩu đầy đủ vì lý do bảo mật
    }

    public static void main(String[] args) {
        SpringApplication.run(ElevatorSystemApplication.class, args);
    }
} 