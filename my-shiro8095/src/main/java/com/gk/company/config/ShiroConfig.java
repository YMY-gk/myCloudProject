package com.gk.company.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.company.Filter.CustomSessionManager;
import com.gk.company.Filter.MyShiro;
import com.gk.company.Filter.RedisManage;
import com.gk.company.Filter.RedisSessionDAO;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置加载
 * shiro架构实现用户的认证和授权
 * 认证：主要是实现用户的登录。
 * 授权：就是对用户实现权限绑定和实现
 * 主要区分：
 *      Subject（主体），realm （连接器） securityManager（安全管理器）
 *
 *
 *
 * @author ruoyi
 */
@Configuration
public class ShiroConfig
{

    /**
     * SecurityManager：安全管理器
     */
    @Bean("securityManager")
    public SecurityManager securityManager(MyShiro myShiro,DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiro);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * Shiro过滤器配置
     * <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl("");
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(" aa");
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/login", "anon");
        // 注册相关
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        // 系统权限列表
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

        shiroFilterFactoryBean.setFilters(filters);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义Realm
     */
    @Bean
    public MyShiro userRealm(RedisTemplate redisTemplate)
    {
        MyShiro userRealm = new MyShiro();

        //增加加密处理
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");//设置加密算法名称
        matcher.setHashIterations(1024);//设置加密的次数
        userRealm.setCredentialsMatcher(matcher);

        //增加缓存
        userRealm.setCacheManager(new RedisManage(redisTemplate));//启用缓存，默认 false
        userRealm.setCachingEnabled(true);//启用缓存，默认 false
        userRealm.setAuthenticationCachingEnabled(true);//启用身份验证缓存，即缓存 AuthenticationInfo 信息，默认 false；
        userRealm.setAuthenticationCacheName("authenticationCache");//缓存 AuthenticationInfo 信息的缓存名称；
        userRealm.setAuthorizationCachingEnabled(true);//启用授权缓存，即缓存 AuthorizationInfo 信息，默认 false；
        userRealm.setAuthorizationCacheName("authorizationCache");//缓存 AuthorizationInfo 信息的缓存名称
        return userRealm;
    }
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认的jdkSerializeable序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
     //   redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();



        return redisTemplate;
    }

    @Bean
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("SHRIOSESSIONID");
        return simpleCookie;
    }

    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(RedisSessionDAO redisSessionDAO) {
        CustomSessionManager  sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setGlobalSessionTimeout(-1000);  //session有效期 默认值1800000 30分钟 1800000毫秒  -1000表示永久
        SimpleCookie simpleCookie = getSimpleCookie();
        simpleCookie.setHttpOnly(true);                 //设置js不可读取此Cookie
        simpleCookie.setMaxAge(3 * 365 * 24 * 60 * 60); //3年 cookie有效期
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }
}
