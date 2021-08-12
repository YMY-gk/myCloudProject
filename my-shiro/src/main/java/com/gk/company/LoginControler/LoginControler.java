package com.gk.company.LoginControler;



import com.gk.commen.param.request.RequestUser;
import com.gk.commen.param.result.LoginResult;
import com.gk.commen.param.result.UserResult;
import com.gk.company.organization.domain.SysUser;
import com.gk.company.organization.service.ISysUserService;
import com.gk.company.organization.service.impl.SysUserService;
import com.gk.company.utils.JwtsUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 *@ClassName LoginControler
 *@Deacription TODO
 *@Author
 *@Date 2021/3/31 17:34
 *@Version 1.0
 **/
@RestController
public class LoginControler {

    @Autowired
    public SysUserService userService;

    @RequestMapping("/login")
    public LoginResult login(String userName, String password, String url){

        LoginResult result = new LoginResult();

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, true);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            System.out.println(subject.getSession().getId());
            HashMap<String,Object> map = new HashMap<String, Object>();
            Session session = subject.getSession();
            map.put("sessionId",session.getId());
            map.put("password",password);
            SysUser suser =  userService.findByName(userName);
            UserResult user = new UserResult();
            BeanUtils.copyProperties(suser,user);
            String jwtToken = JwtsUtils.createJWT(user,map);
            result.setCode("400");
            result.setToken(jwtToken);
            return  result;
        }
        catch (AuthenticationException e)
        {
            e.printStackTrace();

            String msg = "用户或密码错误";
            if (StringUtils.isNotBlank(e.getMessage()))
            {
                msg = e.getMessage();
            }
            result.setCode("301");
            result.setMsg(msg);
            return result;
        }
    }

    @RequestMapping("/register")
    public LoginResult register(RequestUser user){

        LoginResult result = new LoginResult();
        try
        {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            result.setCode("400");
            return  result;
        }
        catch (AuthenticationException e)
        {

            String msg = "用户或密码错误";
            if (StringUtils.isNotBlank(e.getMessage()))
            {
                msg = e.getMessage();
            }
            result.setCode("301");
            return result;
        }
    }
}
