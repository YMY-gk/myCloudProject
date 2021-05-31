package com.gk.company.service;

import com.gk.company.model.User;
import com.gk.company.param.request.RequestUser;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/7 18:31
 */
public interface IUserService {

    User findUserByName(String userName);

    void register(RequestUser user) throws Exception;
}
