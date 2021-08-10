package com.gk.company.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.RoleReq;
import com.gk.commen.param.request.RoleSearch;
import com.gk.company.role.domain.SysRole;
import com.gk.company.role.mapper.SysRoleMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.company.role.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole>  implements ISysRoleService{
    @Resource
    private SysRoleMapper mapper;

    public ResultObject add(RoleReq user){
        SysRole sysUser = new SysRole();
        BeanUtil.copyProperties(user,sysUser);
        mapper.insert(sysUser);
        return  ResultObjectUtil.OK();
    }
    public ResultObject edit(RoleReq user){
        SysRole sysUser = new SysRole();
        BeanUtil.copyProperties(user,sysUser);
        sysUser.setUpdateTime(new Date());
        mapper.updateById(sysUser);
        return  ResultObjectUtil.OK();
    }
    public ResultObject findByid(Integer id){

        SysRole user =mapper.selectById(id);
        return  ResultObjectUtil.OK(user);
    }
    public PageResultObject list(RoleSearch search){
        Page<SysRole> page = new Page<SysRole>(1,15);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>();
        Page user =mapper.selectPage(page,queryWrapper);
        return  PageObjectUtil.OK(user);
    }
}
