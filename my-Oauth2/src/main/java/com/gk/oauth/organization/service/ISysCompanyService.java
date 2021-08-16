package com.gk.oauth.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.param.request.CompanyReq;
import com.gk.commen.param.request.CompanySearch;
import com.gk.oauth.organization.domain.SysCompany;

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
