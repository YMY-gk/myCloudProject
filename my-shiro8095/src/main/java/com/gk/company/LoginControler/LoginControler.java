package com.gk.company.LoginControler;


import com.gk.company.param.request.RequestUser;
import com.gk.company.param.result.LoginResult;
import com.gk.company.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    IUserService serService;

    @RequestMapping("/login")
    public LoginResult login(String userName, String password,String url){

        LoginResult result = new LoginResult();

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, true);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            result.setCode("400");
            result.setToken(token.toString());
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
                serService.register(user);
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
