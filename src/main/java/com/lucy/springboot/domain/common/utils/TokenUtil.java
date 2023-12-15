package com.lucy.springboot.domain.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lucy.springboot.domain.common.api.ResultCode;
import com.lucy.springboot.domain.common.exception.ApiException;
import com.lucy.springboot.entity.User;
import com.lucy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    private static UserService staticUserService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    /**
     * 密钥
     */
    private static final String SECRET = "QLnFu34+fq+NHLAHx7vZBBPr+O2peNWCiJjeLSzTb+0UcRsTKfQ6HCr/VZrxdcfBLCVHmSwlS655e0W5SJ2zIUyPsYmvQijAXqm8hQ/QnACNc4t7bnQwaqBR5jSRdltA";

    /**
     * 过期时间
     */
    private static final Long EXPIRE_TIME = 2 * 60 * 60L; //单位为秒，设置2小时

    /**
     * 生成Token
     * @param userId
     * @param password
     * @return
     */
    public static String genToken(String userId, String password) {
       //设置头部
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        //过期时间
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000);
        // 设置载荷和签名
        return JWT.create().withHeader(header) // 添加头部
                //可以将基本信息放到claims中
                .withClaim("userId", userId)
                .withClaim("password", password)
                .withExpiresAt(expiresAt)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证token，并返回载荷数据
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try{
            //验证token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
            return jwt.getClaims();
        }catch (Exception e){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
    }

    public static User getCurrentUser() {
        try{
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            Map<String, Claim> claims = verifyToken(request.getHeader("token"));
            String userId = claims.get("userId").asString();
            return  staticUserService.getById(Long.valueOf(userId));
        }catch (Exception e){
            return null;
        }
    }
}
