package com.wiocrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "fragments/dashboard :: content";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        return "fragments/analytics :: content";
    }

    @GetMapping("/ecommerce")
    public String ecommerce(Model model) {
        return "fragments/ecommerce :: content";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        return "fragments/projects :: content";
    }

    @GetMapping("/crm")
    public String crm(Model model) {
        return "fragments/crm :: content";
    }

    @GetMapping("/ewallet")
    public String ewallet(Model model) {
        return "fragments/ewallet :: content";
    }

    @GetMapping("/layout")
    public String layout(Model model) {
        model.addAttribute("content", "fragments/dashboard :: content");
        return "layout";
    }
}
