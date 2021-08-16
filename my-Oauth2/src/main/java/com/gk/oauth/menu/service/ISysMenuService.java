package com.gk.oauth.menu.service;

import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.param.request.MenuReq;
import com.gk.commen.param.request.MenuSearch;

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
