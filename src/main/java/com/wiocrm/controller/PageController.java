package com.wiocrm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiocrm.model.UserInfo;
import com.wiocrm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {
    @Autowired
    private CustomUserDetailsService userDetailsService;
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
    @GetMapping("/statCall")
    public String statCall(Model model) {
        return "fragments/statCall :: content";
    }

    @GetMapping("/layout")
    public String layout(HttpServletRequest request, HttpSession session, Model model) {
        model.addAttribute("content", "fragments/dashboard :: content");
        String menuListJson = (String) session.getAttribute("menuListJson");

        UserInfo userInfo = userDetailsService.findUserInfo(request);

        model.addAttribute("userInfo", userInfo != null ? userInfo.getCALL_NO() : "N/A");
        model.addAttribute("menuListJson", menuListJson);
        return "layout";
    }
}
