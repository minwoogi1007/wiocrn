package com.wiocrm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PhoneMapper {

    List<Map<String, Object>>  findProjectAndPersonNameByPhone(@Param("phoneNumber") String phoneNumber, @Param("callNumber") String callNumber);
}
