package com.gk.activity.controller;

import com.gk.activity.controller.Userinterface.LoginClient;
import com.gk.activity.controller.Userinterface.UserClient;
import com.gk.activity.param.result.LoginResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName LoginControler
 *@Deacription TODO
 *@Author
 *@Date 2021/3/31 17:34
 *@Version 1.0
 **/
@RestController
public class UserFeignControler {
    @Autowired
    private UserClient feignClient;
    @Autowired
    private LoginClient loginClient;
    /**
     * 此处的mapping是一级controller，调用方法里边绑定了二级的conroller，相当于用http完成一次转发
     * @return
     */
    @GetMapping("/hello1")
    public String hello(){
        return feignClient.sayHello();
    }

    @GetMapping("/hi1")
    public String hi(){
        return feignClient.sayHi();
    }

    @GetMapping("/haha1")
    public String haha(){
        return feignClient.sayHaha();
    }
    @GetMapping("/hello2")
    @HystrixCommand(fallbackMethod = "helloFallback")//失败时调用默认返回,
    public String hello2(){
        return feignClient.sayHello();
    }

    @GetMapping("/hi2")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "hiFailBack") //失败时调用默认返回,demo中现在我们的客户端调用的接口实际是不存在，所以这个接口会返回下方的默认值
    public String hi2(){
        return feignClient.sayHi();
    }

    @GetMapping("/haha2")
    @HystrixCommand(fallbackMethod = "hahaFailBack")//失败时调用默认返回,demo中现在我们的客户端调用的接口实际是不存在，所以这个接口会返回下方的默认值
    public String haha2(){
        return feignClient.sayHaha();
    }

    @GetMapping("/login")
    //失败时调用默认返回,demo中现在我们的客户端调用的接口实际是不存在，所以这个接口会返回下方的默认值
    @HystrixCommand(fallbackMethod = "hahaFailBack",commandProperties ={
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds" ,value="3000")
    } )
    @ResponseBody
    public LoginResult login( String userName, String password, String url){
        return loginClient.login(userName,  password,  url);
    }


    /**
     *
     *  对应上面的方法，参数必须一致，当访问失败时，hystrix直接回调用此方法
     * @return 失败调用时，返回默认值:
     */
    public String helloFallback(){
        return "您请求的数据没拿到，我是hystrix返回的默认数据--helloxxxx";
    }

    public String hiFailBack(){
        return "您请求的数据没拿到，我是hystrix返回的默认数据--hixxxx";
    }

    public String hahaFailBack(){
        return "您请求的数据没拿到，我是hystrix返回的默认数据--hahaxxxx";
    }

}
