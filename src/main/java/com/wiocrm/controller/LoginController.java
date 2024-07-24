package com.wiocrm.controller;

import com.wiocrm.model.CustomUserDetails;
import com.wiocrm.model.TempUser;
import com.wiocrm.model.User;
import com.wiocrm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/")
    public String log() {
        return "login";
    }
    @GetMapping("/hidden-tetris")
    public String hiddenTetris() {
        return "hidden-tetris";
    }
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            switch (error) {
                case "invalid":
                    model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
                    break;
                case "approval":
                    model.addAttribute("error", "승인 대기 중입니다.");
                    break;
                case "rejected":
                    model.addAttribute("error", "승인이 거부되었습니다.");
                    break;
                default:
                    model.addAttribute("error", "알 수 없는 오류가 발생했습니다.");
            }
        }
        return "login";
    }

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        TempUser tempUser = userDetailsService.findTempUserByUserId(user.getUserId());
        session.setAttribute("tempUser", tempUser);
        model.addAttribute("content", "fragments/dashboard :: content"); // 기본 콘텐츠 설정
        return "main";
    }
}
