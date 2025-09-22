package com.example.api.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
/**

REST Controller for the simple API

This controller provides a simple hello endpoint that returns
a JSON response with a greeting message and current timestamp.
*/
@RestController
@RequestMapping("/api")
public class ApiController {
private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
/**

Hello endpoint that returns a greeting message with timestamp

@return ResponseEntity containing JSON with message and timestamp
*/
@GetMapping("/hello")
public ResponseEntity<Map<String, String>> hello() {
logger.info("Received request to /api/hello endpoint");
try {
Map<String, String> response = new HashMap<>();
response.put("message", "Hello, World!");
response.put("timestamp", LocalDateTime.now().format(TIMESTAMP_FORMATTER));
Copy logger.debug("Returning response: {}", response);
 return ResponseEntity.ok(response);
} catch (Exception e) {
logger.error("Error processing hello request: {}", e.getMessage(), e);
Copy Map<String, String> errorResponse = new HashMap<>();
 errorResponse.put("error", "Internal server error");
 errorResponse.put("timestamp", LocalDateTime.now().format(TIMESTAMP_FORMATTER));
 
 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}

/**

Health check endpoint

@return ResponseEntity with health status
*/
@GetMapping("/health")
public ResponseEntity<Map<String, String>> health() {
logger.debug("Health check requested");
Map<String, String> response = new HashMap<>();
response.put("status", "UP");
response.put("timestamp", LocalDateTime.now().format(TIMESTAMP_FORMATTER));
return ResponseEntity.ok(response);
}
}
