package com.gk.company.organization.service.impl;

import com.gk.company.dao.UserMapper;
import com.gk.company.model.User;
import com.gk.company.param.request.RequestUser;
import com.gk.company.organization.service.IUserService;
import com.gk.company.utils.MD6Util;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/7 18:32
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    public User findUserByName(String userName) {
        return userMapper.findUserByName(userName);
    }

    public void register(RequestUser user) throws Exception {

        String password = user.getPassword();
        User u= userMapper.findUserByName(user.getNickname());
        if(u!=null){
            throw new Exception("该用户已经存在了");
        }
        String salt = MD6Util.getSalt(6);
        Md5Hash md5Hash = new Md5Hash(password,salt,1024);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        userMapper.insertUser(user);
    }
}
