package com.wiocrm.mapper;

import com.wiocrm.model.TempUser;
import com.wiocrm.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);

    TempUser findTempUserByUserId(@Param("userid") String userid);

}
