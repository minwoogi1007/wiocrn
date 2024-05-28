package com.wiocrm.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Collections;

public class User implements UserDetails {
    private String id;
    private String password;
    private String gubn;
    private Date insertTime;
    private String confirmYn;
    private Date confirmTime;
    private String confirmEmp;
    private String userId;
    private String username;
    private String repass;

    // 기본 생성자
    public User() {}

    // 매개변수 생성자
    public User(String id, String password, String gubn, Date insertTime, String confirmYn, Date confirmTime,
                String confirmEmp, String userId, String username, String repass) {
        this.id = id;
        this.password = password;
        this.gubn = gubn;
        this.insertTime = insertTime;
        this.confirmYn = confirmYn;
        this.confirmTime = confirmTime;
        this.confirmEmp = confirmEmp;
        this.userId = userId;
        this.username = username;
        this.repass = repass;
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGubn() {
        return gubn;
    }

    public void setGubn(String gubn) {
        this.gubn = gubn;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getConfirmYn() {
        return confirmYn;
    }

    public void setConfirmYn(String confirmYn) {
        this.confirmYn = confirmYn;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmEmp() {
        return confirmEmp;
    }

    public void setConfirmEmp(String confirmEmp) {
        this.confirmEmp = confirmEmp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }

    // UserDetails 인터페이스 메서드 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보가 필요한 경우, roles 또는 authorities 필드를 추가하여 반환할 수 있습니다.
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
