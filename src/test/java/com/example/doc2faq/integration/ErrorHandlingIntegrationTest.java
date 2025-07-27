package com.example.doc2faq.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErrorHandlingIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturn404PageForNonExistentRoute() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/non-existent-page", String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Page Not Found");
        assertThat(response.getBody()).contains("404");
        assertThat(response.getBody()).contains("Sorry, the page you are looking for could not be found");
        assertThat(response.getBody()).contains("Go Back Home");
    }

    @Test
    void shouldReturn500PageForServerError() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/test/500", String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("Internal Server Error");
        assertThat(response.getBody()).contains("500");
        assertThat(response.getBody()).contains("Sorry, something went wrong on our end");
        assertThat(response.getBody()).contains("Go Back Home");
        assertThat(response.getBody()).contains("Try Again");
    }

    @Test
    void shouldReturnHomePageSuccessfully() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/", String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Doc2FAQ");
        assertThat(response.getBody()).contains("Welcome to the Doc2FAQ application");
        assertThat(response.getBody()).contains("Features");
    }

    @Test
    void shouldServeStaticContentDirectly() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/index.html", String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Welcome to the Doc2FAQ application");
        assertThat(response.getBody()).contains("This service helps you generate FAQs from your documents");
    }
}