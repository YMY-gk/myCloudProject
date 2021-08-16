package com.gk.oauth.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.param.request.UserReq;
import com.gk.commen.param.request.UserSearch;
import com.gk.oauth.organization.domain.SysUser;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author guokui
 * @since 2021-06-08
 */
public interface ISysUserService extends IService<SysUser> {

    public ResultObject add(UserReq user) throws Exception ;
    public ResultObject edit(UserReq user);
    public ResultObject findByid(Integer id);
    public SysUser findByName(String name);
    public PageResultObject list(UserSearch userSearch);
}
