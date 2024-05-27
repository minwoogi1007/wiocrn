package com.wiocrm.controller;

import com.wiocrm.model.User;
import com.wiocrm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String log(@RequestParam(value = "error", required = false) String error, Model model) {
        return "login";
    }
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        User user = userDetailsService.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }

        if (!"Y".equals(user.getConfirmYn())) {
            model.addAttribute("error", "승인 대기 중입니다.");
            return "login";
        }

        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
