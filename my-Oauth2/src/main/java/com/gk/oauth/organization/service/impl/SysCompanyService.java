package com.gk.oauth.organization.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.CompanyReq;
import com.gk.commen.param.request.CompanySearch;
import com.gk.oauth.organization.domain.SysCompany;
import com.gk.oauth.organization.mapper.SysCompanyMapper;
import com.gk.oauth.organization.service.ISysCompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
@Service
public class SysCompanyService extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {
   @Resource
   private SysCompanyMapper mapper;

    public ResultObject add(CompanyReq company){
        SysCompany sysCompany = new SysCompany();
        BeanUtil.copyProperties(company,sysCompany);
        mapper.insert(sysCompany);
        return  ResultObjectUtil.OK();
    }
    public ResultObject edit(CompanyReq company){
        SysCompany sysCompany = new SysCompany();
        BeanUtil.copyProperties(company,sysCompany);
        mapper.updateById(sysCompany);
        return  ResultObjectUtil.OK();
    }
    public ResultObject findByid(Integer id){

        SysCompany user =mapper.selectById(id);
        return  ResultObjectUtil.OK(user);
    }
    public PageResultObject list(CompanySearch companySearch){
        Page<SysCompany> page = new Page<SysCompany>(1,15);
        QueryWrapper<SysCompany> queryWrapper = new QueryWrapper<SysCompany>();
        Page user =mapper.selectPage(page,queryWrapper);
        return  PageObjectUtil.OK(user);
    }
}
