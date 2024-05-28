package com.wiocrm.service;

import com.wiocrm.model.User;
import com.wiocrm.model.TempUser;
import com.wiocrm.mapper.UserMapper;
import com.wiocrm.model.CustomUserDetails;
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
        return new CustomUserDetails(user);
    }

    // Existing methods for finding user and temp user
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public TempUser findTempUserByUserId(String userId) {
        return userMapper.findTempUserByUserId(userId);
    }
}
