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
    public String dashboard(HttpSession session,Model model) {
        String userPosition = (String) session.getAttribute("userPosition");
        model.addAttribute("userPosition", userPosition);
        // API 호출하여 데이터 가져오기

        return "fragments/dashboard :: content";

    }

    @GetMapping("/analytics")
    public String analytics(HttpSession session, Model model) {
        return "fragments/analytics :: content";
    }

    @GetMapping("/ecommerce")
    public String ecommerce(HttpSession session,Model model) {
        return "fragments/ecommerce :: content";
    }

    @GetMapping("/projects")
    public String projects(HttpSession session,Model model) {
        return "fragments/projects :: content";
    }

    @GetMapping("/crm")
    public String crm(HttpSession session,Model model) {
        return "fragments/crm :: content";
    }

    @GetMapping("/ewallet")
    public String ewallet(HttpSession session,Model model) {
        return "fragments/ewallet :: content";
    }
    @GetMapping("/statCall")
    public String statCall(HttpSession session, Model model) {
        return "fragments/statCall :: content";
    }

    @GetMapping("/layout")
    public String layout(HttpServletRequest request, HttpSession session, Model model) {
        model.addAttribute("content", "fragments/dashboard :: content");
        String menuListJson = (String) session.getAttribute("menuListJson");

        UserInfo userInfo = userDetailsService.findUserInfo(request);

        model.addAttribute("userInfo", userInfo != null ? userInfo.getCALL_NO() : "N/A");
        model.addAttribute("userPosition", userInfo != null ? userInfo.getPOSITION() : "N/A");
        model.addAttribute("menuListJson", menuListJson);
        return "layout";
    }
}
