package com.gk.company.organization.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.CompanyReq;
import com.gk.commen.param.request.CompanySearch;
import com.gk.company.organization.domain.SysCompany;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 企业表 服务类
 * </p>
 *
 * @author guokui
 * @since 2021-06-09
 */
public interface ISysCompanyService extends IService<SysCompany> {

    public ResultObject add(CompanyReq company);
    public ResultObject edit(CompanyReq company);
    public ResultObject findByid(Integer id);
    public PageResultObject list(CompanySearch companySearch);
}
