package com.gk.commen.utils;

import com.gk.commen.param.result.UserResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.User;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author yumuyi
 * @version 1.0
 * @date 2021/6/10 0:18
 *
 * jwt包含一下三部分
 * 头部（Header）
 * 有效载荷（Payload）
 * 签名(Signature)
 *头部（Header）
 * 头部用于描述关于该JWT的最基本的信息，
 * 通常由两部分组成：令牌的类型（即JWT）和所使用的签名算法。
 *payload，自定义值.
 * json转化为字符串，然后做base64url的转码
 *第三段字符串Signature
 * 前两部分的密文拼接起来
 * 对前2部分的密文进行HS256加密 + 加盐
 * 对HS256加密后的密文再做base64url加密
 *
 */
public class JwtsUtils {
    private static   String key="zhangsan";
    private  static Long llt=36000000L;
    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param user      登录成功的user对象
     * @return
     */
    public static String createJWT(UserResult user,HashMap map) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。
        // 它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String secretkey = key;
        //生成签发人
        String subject = user.getUserName();

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(map)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secretkey);
        if (llt >= 0) {
            long expMillis = nowMillis + llt;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * Token的解密
     * @param token 加密后的token
     * @return
     */
    public static Claims parseJWT(String token) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String secretkey = key;

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(secretkey)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }


    /**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     * @param token
     * @param user
     * @return
     */
    public static Boolean isVerify(String token, User user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getPassword();
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();

        if (claims.get("password").equals(user.getPassword())) {
            return true;
        }
        return false;
    }

}
