package com.lucy.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucy.springboot.domain.common.api.CommonResult;
import com.lucy.springboot.domain.common.exception.ApiException;
import com.lucy.springboot.domain.common.utils.TokenUtil;
import com.lucy.springboot.domain.dto.UserLoginParam;
import com.lucy.springboot.domain.dto.UserPageParam;
import com.lucy.springboot.domain.vo.UserVo;
import com.lucy.springboot.entity.User;
import com.lucy.springboot.mapper.UserMapper;
import com.lucy.springboot.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private Sequence sequence;

    public boolean saveUser(User user) {
        //新增用户
        if(user.getId()==null) {
            user.setId(sequence.nextId());
            user.setPassword("test123");
            user.setCreateTime(new Date());
        }
        return saveOrUpdate(user);
    }

    @Override
    public IPage<User> selectPage(UserPageParam param) {
        IPage<User> page = new Page<>(param.getPageParam().getPageNum(), param.getPageParam().getPageSize());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(param.getUsername()!=null && !"".equals(param.getUsername()),"username", param.getUsername())
                .like(param.getEmail()!=null && !"".equals(param.getEmail()),"email", param.getEmail())
                .like(param.getAddress()!=null && !"".equals(param.getAddress()),"address", param.getAddress())
                .orderByDesc("create_time");
        return this.page(page, queryWrapper);
    }

    @Override
    public UserVo login(UserLoginParam param) {
        User user = this.getOne(Wrappers.lambdaQuery(User.class)
                                .eq(User::getUsername, param.getUsername())
                                .eq(User::getPassword, param.getPassword()));
        if(user==null)
            throw new ApiException("账号或密码错误");

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setToken(TokenUtil.genToken(user.getId().toString(), user.getPassword()));
        return userVo;
    }

    @Override
    public Long register(UserLoginParam user) {
        //验证密码是否一致
        if(!user.getPassword().equals(user.getConfirmPassword()))
            throw new ApiException("两次密码不一致");

        //验证用户名是否已存在
        if(this.getOne(Wrappers.lambdaQuery(User.class)
               .eq(User::getUsername, user.getUsername()))!=null)
            throw new ApiException("用户名已存在");

        //保存用户
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setId(sequence.nextId());
        this.save(newUser);
        return newUser.getId();
    }
}




