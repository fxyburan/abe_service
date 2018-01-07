package com.vontroy.common.mybatis.mapper;

import com.vontroy.common.mybatis.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(name, create_time) values(#{name}, current_date)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUserInfo(int id);
}
