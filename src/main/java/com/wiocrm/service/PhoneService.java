package com.wiocrm.service;

import com.wiocrm.mapper.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhoneService {

    private final PhoneMapper phoneMapper;
    @Autowired
    public PhoneService(PhoneMapper phoneMapper) {
        this.phoneMapper = phoneMapper;
    }

    public Map<String, String> getPhoneInfo(String pNo, String cNo) {
        Map<String, String> info = new HashMap<>();
        List<Map<String, Object>> results = phoneMapper.findProjectAndPersonNameByPhone(pNo, cNo);

        if (!results.isEmpty()) {
            Map<String, Object> result = results.get(0);
            String projectName = (String) result.get("PROJECT_NAME");
            String personName = (String) result.get("PERSON_NAME");

            info.put("projectName", projectName);
            info.put("personName", personName);
        }
        return info;
    }
}
