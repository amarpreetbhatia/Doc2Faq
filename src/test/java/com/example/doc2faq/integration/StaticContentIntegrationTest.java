package com.example.doc2faq.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class HomePageIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void shouldServeHomePageWithThymeleafTemplate() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Doc2FAQ")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Welcome to the Doc2FAQ application")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("This service helps you generate FAQs from your documents")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Features")));
    }

    @Test
    void shouldServeStaticIndexHtmlWhenAccessedDirectly() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        mockMvc.perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Welcome to the Doc2FAQ application")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("This service helps you generate FAQs from your documents")));
    }
}