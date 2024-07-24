package com.wiocrm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiocrm.model.CustomUserDetails;
import com.wiocrm.model.User;
import com.wiocrm.model.UserInfo;
import com.wiocrm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/resources/**", "/css/**", "/js/**","/hidden-tetris").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/login?error=invalid")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired")
                .and()
                .invalidSessionUrl("/login?invalid")
                .sessionFixation().migrateSession()
                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .tokenValiditySeconds(3600); // 1시간

        http.sessionManagement()
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .expiredUrl("/login?expired");
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            System.out.println("User confirmed status: " + user.getConfirmYn());

            if ("N".equals(user.getConfirmYn())) {
                System.out.println("Redirecting to /login?error=approval");
                response.sendRedirect("/login?error=approval");
            } else if ("C".equals(user.getConfirmYn())) {
                System.out.println("Redirecting to /login?error=rejected");
                response.sendRedirect("/login?error=rejected");
            } else {
                // 메뉴 데이터를 JSON 문자열로 변환하여 세션에 저장
                List<Map<String, Object>> menuData = userDetailsService.getUserMenu(user.getUserId());
                ObjectMapper mapper = new ObjectMapper();
                String menuListJson = mapper.writeValueAsString(menuData);

                // findUserInfo를 호출하여 사용자 추가 정보를 가져옴
                UserInfo userInfo = userDetailsService.findUserInfoSession(user.getUserId());


                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                session.setAttribute("menuListJson", menuListJson);
                session.setAttribute("userPosition", userDetails.getPosition());

                // 세션 만료 시간 설정 (1시간)
                session.setMaxInactiveInterval(3600);

                response.sendRedirect("/layout");
            }
        };
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
