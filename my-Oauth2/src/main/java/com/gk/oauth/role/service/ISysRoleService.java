package com.gk.oauth.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.param.request.RoleReq;
import com.gk.commen.param.request.RoleSearch;
import com.gk.oauth.role.domain.SysRole;

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
