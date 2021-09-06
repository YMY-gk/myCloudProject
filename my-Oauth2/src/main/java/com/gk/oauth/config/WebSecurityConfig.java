package com.gk.oauth.config;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/6/1 23:26
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //获取登录用户
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("zhangsan").password("123456").authorities("gk").build());
//        return manager;
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    //配置密码规则
    //配置认证规则
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()//认证配置
                .antMatchers("/user/**") //释放路径
                .permitAll() // 指定 URL 无需保护。 无需认证授权
                .and()
                .formLogin()
                      .loginProcessingUrl("/user/login") // 自定义的登录接口
                      .defaultSuccessUrl("/user/sayHi")  //利用重定向避免405错误 如果不是这样会出现post请求接口路径未找到
                      .permitAll()//允许表单登录
                .and()
                      .authorizeRequests()
                      .anyRequest()
                      .authenticated() //需要认证授权
                .and().csrf().disable();//其他请求需要认证
    }


}
