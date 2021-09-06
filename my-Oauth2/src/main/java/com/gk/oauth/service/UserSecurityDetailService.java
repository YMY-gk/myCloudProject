package com.gk.oauth.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.commen.entity.PageObjectUtil;
import com.gk.commen.entity.PageResultObject;
import com.gk.commen.entity.ResultObject;
import com.gk.commen.entity.ResultObjectUtil;
import com.gk.commen.param.request.UserReq;
import com.gk.commen.param.request.UserSearch;
import com.gk.commen.utils.MD6Util;
import com.gk.oauth.menu.mapper.SysMenuMapper;
import com.gk.oauth.organization.domain.SysUser;
import com.gk.oauth.organization.mapper.SysUserMapper;
import com.gk.oauth.organization.service.ISysUserService;
import com.gk.oauth.role.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author guokui
 * @since 2021-06-08
 */
@Service
@Slf4j
public class UserSecurityDetailService implements UserDetailsService {
    @Resource
    private  SysUserMapper usermapper;
    @Resource
    private SysRoleMapper rolemapper;
    @Resource
    private SysMenuMapper menumapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1、判断用户名是否存在
        if (!"user".equals(username)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(SysUser::getLoginName,username);
        SysUser user = usermapper.selectOne(userQueryWrapper);
        //2、 从数据库中获取的密码 atguigu 的密文
        String pwd =user.getPassword();
        //3、获取列表权限和角色

       // =menumapper.selectListByUserId(user.getUserId());

        // 第三个参数表示权限
        return new User(username,new BCryptPasswordEncoder().encode(pwd), AuthorityUtils.commaSeparatedStringToAuthorityList("user,ROLE_role1"));
    }
}
