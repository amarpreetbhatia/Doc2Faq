package com.example.doc2faq.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Test controller for simulating errors during development and testing.
 * Only active in non-production profiles.
 */
@Controller
@Profile("!prod")
public class TestController {

    @GetMapping("/test/500")
    public String simulateServerError() {
        throw new RuntimeException("Simulated server error for testing purposes");
    }
}