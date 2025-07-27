package com.example.doc2faq.controller;

import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomErrorController.class)
class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404ErrorPage() throws Exception {
        mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"))
                .andExpect(model().attribute("title", "Page Not Found"))
                .andExpect(model().attribute("message", "Sorry, the page you are looking for could not be found."))
                .andExpect(model().attribute("statusCode", "404"));
    }

    @Test
    void shouldReturn500ErrorPage() throws Exception {
        mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/500"))
                .andExpect(model().attribute("title", "Internal Server Error"))
                .andExpect(model().attribute("message", "Sorry, something went wrong on our end. Please try again later."))
                .andExpect(model().attribute("statusCode", "500"));
    }

    @Test
    void shouldReturnDefaultErrorPageForUnknownStatus() throws Exception {
        mockMvc.perform(get("/error")
                .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value()))
                .andExpect(status().isOk())
                .andExpect(view().name("error/500"))
                .andExpect(model().attribute("title", "Error"))
                .andExpect(model().attribute("message", "An unexpected error occurred. Please try again later."))
                .andExpect(model().attribute("statusCode", "400"));
    }

    @Test
    void shouldHandleErrorWithoutStatusCode() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/500"))
                .andExpect(model().attribute("title", "Error"))
                .andExpect(model().attribute("message", "An unexpected error occurred. Please try again later."))
                .andExpect(model().attribute("statusCode", "Unknown"));
    }
}