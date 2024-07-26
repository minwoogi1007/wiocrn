package com.wiocrm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiocrm.model.UserInfo;
import com.wiocrm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/consultant/type/status")
    public String consultant_type_status(HttpSession session, Model model) {
        return "fragments/consultant_type_status :: content";
    }

    @GetMapping("/consultant/progress/status")
    public String consultant_progress_status(HttpSession session, Model model) {
        return "fragments/consultant_progress_status :: content";
    }

    @GetMapping("/company/type/status")
    public String company_type_status(HttpSession session, Model model) {
        return "fragments/company_type_status :: content";
    }

    @GetMapping("/company/progress/status")
    public String company_progress_status(HttpSession session, Model model) {
        return "fragments/company_progress_status :: content";
    }

    @GetMapping("/call/processing/status")
    public String call_processing_status(HttpSession session, Model model) {
        return "fragments/call_processing_status :: content";
    }

    @GetMapping("/company/time/consulting/status")
    public String company_time_consulting_status(HttpSession session, Model model) {
        return "fragments/company_time_consulting_status :: content";
    }

    @GetMapping("/consultant/time/consulting/status")
    public String consultant_time_consulting_status(HttpSession session, Model model) {
        return "fragments/consultant_time_consulting_status :: content";
    }

    @GetMapping("/company/time/call/status")
    public String company_time_call_status(HttpSession session, Model model) {
        return "fragments/company_time_call_status :: content";
    }

    @GetMapping("/company/consultant/consulting/status")
    public String company_consultant_consulting_status(HttpSession session, Model model) {
        return "fragments/company_consultant_consulting_status :: content";
    }

    @GetMapping("/unreceived/customer/count")
    public String unreceived_customer_count(HttpSession session, Model model) {
        return "fragments/unreceived_customer_count :: content";
    }

    @GetMapping("/company/purchase/status")
    public String company_purchase_status(HttpSession session, Model model) {
        return "fragments/statCall :: content";
    }

    @GetMapping("/new/consulting/type/status")
    public String new_consulting_type_status(HttpSession session, Model model) {
        return "fragments/new_consulting_type_status :: content";
    }

    @GetMapping("/new/call/volume/status")
    public String call_volume_status(HttpSession session, Model model) {
        return "fragments/call_volume_status :: content";
    }

    @GetMapping("/consultant/company/consulting/status")
    public String consultant_company_consulting_status(HttpSession session, Model model) {
        return "fragments/consultant_company_consulting_status :: content";
    }

    @GetMapping("/company/consultant/involved/status")
    public String company_consultant_involved_status(HttpSession session, Model model) {
        return "fragments/company_consultant_involved_status :: content";
    }

    @GetMapping("/call/status")
    public String call_status(HttpSession session, Model model) {
        return "fragments/call_status :: content";
    }

    @GetMapping("/unreceived/status")
    public String unreceived_status(HttpSession session, Model model) {
        return "fragments/unreceived_status :: content";
    }

    @GetMapping("/consultation-registration")
    public String consultationRegistration(@RequestParam String pNo, @RequestParam String cNo,
                                           @RequestParam String projectName, @RequestParam String personName, Model model) {
        model.addAttribute("pNo", pNo);
        model.addAttribute("cNo", cNo);
        model.addAttribute("projectName", projectName);
        model.addAttribute("personName", personName);
        return "fragments/consultation-template :: content";
    }
}
