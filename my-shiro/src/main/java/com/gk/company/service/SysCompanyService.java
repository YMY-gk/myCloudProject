package com.gk.company.service;

import com.gk.company.domain.SysCompany;
import com.gk.company.mapper.SysCompanyMapper;
import com.gk.company.service.impl.ISysCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
