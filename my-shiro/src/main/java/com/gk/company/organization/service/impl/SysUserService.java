package com.gk.company.organization.service.impl;

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
import com.gk.company.organization.mapper.SysUserMapper;
import com.gk.company.organization.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author guokui
 * @since 2021-06-08
 */
@Service
@Slf4j
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Resource
    private  SysUserMapper mapper;

    public ResultObject add(UserReq user) throws Exception {

        String password = user.getPassword();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUser::getLoginName,user.getLoginName());
        SysUser u= mapper.selectOne(queryWrapper);
        if(u!=null){
            throw new Exception("该用户已经存在了");
        }
        SysUser suser = new SysUser();
        BeanUtil.copyProperties(user,suser);
        String salt = MD6Util.getSalt(6);
        Md5Hash md5Hash = new Md5Hash(password,salt,1024);
        suser.setPassword(md5Hash.toHex());
        suser.setSalt(salt);
        mapper.insert(suser);
        return  ResultObjectUtil.OK();
    }
    public ResultObject edit(UserReq user){
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(user,sysUser);
        sysUser.setUpdateTime(new Date());
        mapper.updateById(sysUser);
        return  ResultObjectUtil.OK();
    }
    public ResultObject findByid(Integer id){

        SysUser user =mapper.selectById(id);
        return  ResultObjectUtil.OK(user);
    }
    public SysUser findByName(String name){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUser::getLoginName,name);
        return  mapper.selectOne(queryWrapper);
    }
    public PageResultObject list(UserSearch userSearch){
        Page<SysUser> page = new Page<SysUser>(1,15);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        Page user =mapper.selectPage(page,queryWrapper);
        return  PageObjectUtil.OK(user);
    }
}
