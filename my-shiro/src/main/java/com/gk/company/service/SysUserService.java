package com.gk.company.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.UserReq;
import com.gk.commen.param.request.UserSearch;
import com.gk.company.domain.SysUser;
import com.gk.company.mapper.SysUserMapper;
import com.gk.company.service.impl.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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

    public ResultObject add(UserReq user){
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(user,sysUser);
        mapper.insert(sysUser);
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
    public PageResultObject list(UserSearch userSearch){
        Page<SysUser> page = new Page<SysUser>(1,15);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        Page user =mapper.selectPage(page,queryWrapper);
        return  PageObjectUtil.OK(user);
    }
}
