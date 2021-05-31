package com.gk.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/4/12 0:53
 */
public class shiroTest {

    public static void main(String[] args) {
        //创建DefaultSecurityManager安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //注入授权和认证类realm
        IniRealm realm = new IniRealm("classpath:use.ini");
        //注入授权和认证类realm
        securityManager.setRealm(realm);
        //向安全工具注册安全处理器
        SecurityUtils.setSecurityManager(securityManager);
        //安全工具中创建主体subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123456");

        subject.login(token );
    }
}
