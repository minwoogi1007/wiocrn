package com.wiocrm.service;

import com.wiocrm.model.User;
import com.wiocrm.model.TempUser;
import com.wiocrm.mapper.UserMapper;
import com.wiocrm.model.CustomUserDetails;
import com.wiocrm.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<Map<String, Object>> menuList = userMapper.getUserMenu(username);
        return new CustomUserDetails(user, menuList);
    }

    // Existing methods for finding user and temp user
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }


    public TempUser findTempUserByUserId(String userId) {
        return userMapper.findTempUserByUserId(userId);
    }
    public List<Map<String, Object>> getUserMenu(String username) {
        return userMapper.getUserMenu(username);
    }

    public UserInfo findUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userId = null;
        System.out.println("userId from session111============================================================" + userId);
        if (user != null) {
            userId = user.getUserId();  // Assuming 'getUserId()' method exists in your User class
            System.out.println("userId from session222============================================================" + userId);
        }

        if (userId != null) {
            System.out.println("userId from session333============================================================" + userId);
            return userMapper.findUserInfo(userId);
        }

        return null;
    }
}
