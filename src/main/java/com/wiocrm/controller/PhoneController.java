package com.wiocrm.controller;

import com.wiocrm.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getPhoneInfo(@RequestParam String pNo, @RequestParam String cNo) {
        Map<String, String> info = phoneService.getPhoneInfo(pNo, cNo);
        return ResponseEntity.ok(info);
    }
}
