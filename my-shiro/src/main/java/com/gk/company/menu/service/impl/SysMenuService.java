package com.gk.company.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.MenuReq;
import com.gk.commen.param.request.MenuSearch;
import com.gk.company.menu.domain.SysMenu;
import com.gk.company.menu.mapper.SysMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.company.menu.service.ISysMenuService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Resource
    private SysMenuMapper mapper;

    public ResultObject add(MenuReq user){
        SysMenu sysUser = new SysMenu();
        BeanUtil.copyProperties(user,sysUser);
        mapper.insert(sysUser);
        return  ResultObjectUtil.OK();
    }
    public ResultObject edit(MenuReq user){
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(user,sysMenu);
        sysMenu.setUpdateTime(new Date());
        mapper.updateById(sysMenu);
        return  ResultObjectUtil.OK();
    }
    public ResultObject findByid(Integer id){

        SysMenu user =mapper.selectById(id);
        return  ResultObjectUtil.OK(user);
    }
    public PageResultObject list(MenuSearch search){
        Page<SysMenu> page = new Page<SysMenu>(1,15);
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>();
        IPage user =mapper.selectPage(page,queryWrapper);
        return  PageObjectUtil.OK(user);
    }
}
