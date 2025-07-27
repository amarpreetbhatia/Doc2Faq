package com.example.doc2faq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Doc2FAQ");
        model.addAttribute("message", "Welcome to the Doc2FAQ application. This service helps you generate FAQs from your documents.");
        return "index";
    }
}