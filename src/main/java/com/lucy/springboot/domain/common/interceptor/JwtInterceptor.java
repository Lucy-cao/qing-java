package com.lucy.springboot.domain.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.Claim;
import com.lucy.springboot.domain.common.api.ResultCode;
import com.lucy.springboot.domain.common.exception.ApiException;
import com.lucy.springboot.domain.common.utils.TokenUtil;
import com.lucy.springboot.entity.User;
import com.lucy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * jwt拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token，对token进行数据校验
        String token = request.getHeader("token");
        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        //验证token，并返回载荷值
        Map<String, Claim> userData = TokenUtil.verifyToken(token);
        //获取载荷中的用户id和密码
        String userId, password;
        try{
            userId = userData.get("userId").asString();
            password = userData.get("password").asString();
        }catch (Exception e){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        //根据userId获取用户
        User user = userService.getById(userId);
        if(user==null){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        //验证密码是否正确
        if(!user.getPassword().equals(password)){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }

        return true;
    }
}
