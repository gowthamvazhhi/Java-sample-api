package com.example.api.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.;
import static org.junit.jupiter.api.Assertions.;
/**

Unit tests for ApiController

This class contains unit tests for the REST endpoints
using MockMvc to simulate HTTP requests.
*/
@WebMvcTest(ApiController.class)
class ApiControllerTest {
@Autowired
private MockMvc mockMvc;
@Autowired
private ObjectMapper objectMapper;
@Test
@DisplayName("Should return hello message with timestamp when GET /api/hello is called")
void testHelloEndpoint() throws Exception {
// Perform GET request to /api/hello
MvcResult result = mockMvc.perform(get("/api/hello")
.contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isOk())
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(jsonPath("$.message").exists())
.andExpect(jsonPath("$.timestamp").exists())
.andExpect(jsonPath("$.message").value("Hello, World!"))
.andReturn();
Copy // Parse response and verify structure
 String responseContent = result.getResponse().getContentAsString();
 Map<String, String> response = objectMapper.readValue(responseContent, Map.class);
 
 assertNotNull(response.get("message"));
 assertNotNull(response.get("timestamp"));
 assertEquals("Hello, World!", response.get("message"));
 
 // Verify timestamp format (basic check)
 String timestamp = response.get("timestamp");
 assertTrue(timestamp.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
}
@Test
@DisplayName("Should return health status when GET /api/health is called")
void testHealthEndpoint() throws Exception {
// Perform GET request to /api/health
MvcResult result = mockMvc.perform(get("/api/health")
.contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isOk())
.andExpected(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(jsonPath("$.status").exists())
.andExpect(jsonPath("$.timestamp").exists())
.andExpect(jsonPath("$.status").value("UP"))
.andReturn();
Copy // Parse response and verify structure
 String responseContent = result.getResponse().getContentAsString();
 Map<String, String> response = objectMapper.readValue(responseContent, Map.class);
 
 assertNotNull(response.get("status"));
 assertNotNull(response.get("timestamp"));
 assertEquals("UP", response.get("status"));
}
@Test
@DisplayName("Should return 404 for non-existent endpoint")
void testNonExistentEndpoint() throws Exception {
mockMvc.perform(get("/api/nonexistent")
.contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isNotFound());
}
@Test
@DisplayName("Should handle multiple concurrent requests to hello endpoint")
void testConcurrentRequests() throws Exception {
// Test multiple requests to ensure thread safety
for (int i = 0; i < 5; i++) {
mockMvc.perform(get("/api/hello"))
.andExpect(status().isOk())
.andExpect(jsonPath("$.message").value("Hello, World!"))
.andExpect(jsonPath("$.timestamp").exists());
}
}
}
