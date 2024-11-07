package com.lushnikova;

import org.audit_logging.annotations.EnableAuditLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка старта
 */
@SpringBootApplication
@EnableAuditLogging
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
