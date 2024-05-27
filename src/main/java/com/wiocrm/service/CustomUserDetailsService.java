package com.wiocrm.service;

import com.wiocrm.model.User;
import com.wiocrm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserId())
                .password(user.getPassword())
                .roles("USER")
                .disabled(!"Y".equals(user.getConfirmYn()))
                .build();
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
