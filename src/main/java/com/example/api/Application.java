package com.example.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**

Main Spring Boot Application class for tata-java-simple-api

This class serves as the entry point for the Spring Boot application.
It enables auto-configuration, component scanning, and configuration properties.
*/
@SpringBootApplication
public class Application {
private static final Logger logger = LoggerFactory.getLogger(Application.class);
/**

Main method to start the Spring Boot application

@param args command line arguments
*/
public static void main(String[] args) {
logger.info("Starting tata-java-simple-api application...");
try {
SpringApplication.run(Application.class, args);
logger.info("Application started successfully!");
} catch (Exception e) {
logger.error("Failed to start application: {}", e.getMessage(), e);
System.exit(1);
}
}
}
