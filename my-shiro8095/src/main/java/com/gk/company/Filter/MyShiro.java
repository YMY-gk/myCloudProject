package com.gk.company.Filter;

import com.gk.company.model.User;
import com.gk.company.organization.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/11 23:36
 */
public class MyShiro extends AuthorizingRealm {

    @Autowired
    IUserService userService;
    //用户的权限信息包含roles角色和permission权限两部分，我这里只使用了角色进行进行权限控制
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //principals.getPrimaryPrincipal()获得的就是当前用户名
        System.out.println("授权处理---------------------------------------》"+principals.getPrimaryPrincipal());
        User user = userService.findUserByName((String) principals.getPrimaryPrincipal());
        if (principals == null || StringUtils.isBlank((String) principals.getPrimaryPrincipal())) {
            return null;
        }
        //将用户角色信息传入SimpleAuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo= new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("admin:add");
        simpleAuthorizationInfo.addStringPermission("admin:edit");
        return simpleAuthorizationInfo;
    }

    //token实际就是在login时传入的UsernamePasswordToken
    //getPrincipal()中只执行了getUsername(),getCredentials()只执行了getPassword()
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token == null||StringUtils.isBlank((String) token.getPrincipal())) {
            return null;
        }
        //根据token中的用户名查库，获得user对象
        User user = userService.findUserByName((String) token.getPrincipal());
        if (user == null) {
            return null;
        }
        //SimpleAuthenticationInfo代表该用户的认证信息，其实就是数据库中的用户名、密码、加密密码使用的盐
        //存在数据库中的密码是对用户真是密码通过md5加盐加密得到的，保证安全，及时数据泄露，也得不到真正的用户密码
        //getName()返回该realm的名字，代表该认证信息的来源是该realm，作用不大，一般都是单realm
        //该方法返回后，上层会对token和SimpleAuthenticationInfo进行比较，首先比较Principal()，然后将token的Credentials
        //进行md5加上SimpleAuthenticationInfo中的盐加密，加密结果和SimpleAuthenticationInfo的Credentials比较
        return new SimpleAuthenticationInfo(
                user.getNickname(), user.getPassword(), new MyByteSource(user.getSalt()), getName());
    }
}
