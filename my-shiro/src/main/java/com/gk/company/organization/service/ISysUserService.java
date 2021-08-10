package com.gk.company.organization.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.UserReq;
import com.gk.commen.param.request.UserSearch;
import com.gk.commen.utils.MD6Util;
import com.gk.company.organization.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Date;

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
