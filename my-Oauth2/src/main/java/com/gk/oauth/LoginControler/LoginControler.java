package com.gk.oauth.LoginControler;


import com.gk.commen.param.request.UserReq;
import com.gk.commen.param.result.LoginResult;
import com.gk.oauth.organization.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName LoginControler
 * @Deacription TODO
 * @Author
 * @Date 2021/3/31 17:34
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class LoginControler {

    @Autowired
    public ISysUserService userService;

    @RequestMapping("/login")
    public LoginResult login(String userName, String password, String url) {
        log.error(userName+"-----------------"+password);
        LoginResult result = new LoginResult();
        return result;
    }

    @RequestMapping("/register")
    public LoginResult register(UserReq user) {

        LoginResult result = new LoginResult();
        try {
            try {
                userService.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.setCode("200");
            return result;
        } catch (Exception e) {

            String msg = "用户或密码错误";
            if (StringUtils.isNotBlank(e.getMessage())) {
                msg = e.getMessage();
            }
            result.setCode("301");
            return result;
        }
    }

    @RequestMapping("/logout")
    public LoginResult loginout() {

        LoginResult result = new LoginResult();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode("200");
        return result;
    }
}
