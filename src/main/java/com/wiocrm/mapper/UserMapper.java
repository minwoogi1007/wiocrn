package com.wiocrm.mapper;

import com.wiocrm.model.TempUser;
import com.wiocrm.model.User;
import com.wiocrm.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    TempUser findTempUserByUserId(@Param("userid") String userid);
    List<Map<String, Object>>getUserMenu(@Param("username") String username);

    UserInfo findUserInfo(@Param("username") String username);
    @Select("SELECT POSITION FROM N_TEMP01 WHERE USERID = #{userId}")
    String findPositionByUserId(@Param("userId") String userId);
}
