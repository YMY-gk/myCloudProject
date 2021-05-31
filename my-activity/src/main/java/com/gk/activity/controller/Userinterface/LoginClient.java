package com.gk.activity.controller.Userinterface;

import com.gk.activity.param.result.LoginResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 这个接口相当于把原来的服务提供者项目当成一个Service类
 * @author  yumuyi
 * @date  2021/4/5 21:24
 * @version 1.0
 */
@FeignClient("company")
public interface LoginClient {
    /**
     * Feign中没有原生的@GetMapping/@PostMapping/@DeleteMapping/@PutMapping，要指定需要用method进行
     *
     *
     * 接口上方用requestmapping指定是服务提供者的哪个controller提供服务
     */

    @RequestMapping(value= "/login",method= RequestMethod.GET)
    public LoginResult login(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("url") String url);

}