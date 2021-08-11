package com.gk.company.menu.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.MenuReq;
import com.gk.commen.param.request.MenuSearch;
import com.gk.company.menu.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
public interface ISysMenuService  {
    public ResultObject add(MenuReq user);
    public ResultObject edit(MenuReq user);
    public ResultObject findByid(Integer id);
    public PageResultObject list(MenuSearch search);
}
