package com.gk.company.dao;

import com.gk.company.model.User;
import com.gk.company.param.request.RequestUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface UserMapper
{

    User findUserByName(@Param("nickname") String userName);

    void insertUser(RequestUser user);
}
