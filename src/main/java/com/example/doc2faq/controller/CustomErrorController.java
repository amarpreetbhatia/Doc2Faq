package com.example.doc2faq.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("title", "Page Not Found");
                model.addAttribute("message", "Sorry, the page you are looking for could not be found.");
                model.addAttribute("statusCode", "404");
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("title", "Internal Server Error");
                model.addAttribute("message", "Sorry, something went wrong on our end. Please try again later.");
                model.addAttribute("statusCode", "500");
                return "error/500";
            }
        }
        
        // Default error page for other status codes
        model.addAttribute("title", "Error");
        model.addAttribute("message", "An unexpected error occurred. Please try again later.");
        model.addAttribute("statusCode", status != null ? status.toString() : "Unknown");
        return "error/500";
    }
}