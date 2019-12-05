package com.springboot.mapper;

import com.springboot.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    UserEntity getUserByNameAndPassword(String userName, String password);
}
