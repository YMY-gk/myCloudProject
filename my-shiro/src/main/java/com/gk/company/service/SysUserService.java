package com.gk.company.service;

import com.gk.company.domain.SysUser;
import com.gk.company.mapper.SysUserMapper;
import com.gk.company.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author guokui
 * @since 2021-06-08
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
