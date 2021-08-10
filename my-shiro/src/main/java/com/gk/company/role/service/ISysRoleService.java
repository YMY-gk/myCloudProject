package com.gk.company.role.service;

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
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
public interface ISysRoleService extends IService<SysRole> {
    public ResultObject add(RoleReq user);
    public ResultObject edit(RoleReq user);
    public ResultObject findByid(Integer id);
    public PageResultObject list(RoleSearch search);
}
