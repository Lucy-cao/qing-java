package com.lucy.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucy.springboot.domain.common.api.CommonResult;
import com.lucy.springboot.domain.dto.UserLoginParam;
import com.lucy.springboot.domain.dto.UserPageParam;
import com.lucy.springboot.domain.vo.UserVo;
import com.lucy.springboot.entity.User;
import com.lucy.springboot.mapper.UserMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface UserService extends IService<User> {
    /**
     * 保存用户
     * @param user
     * @return
     */
    public boolean saveUser(User user);

    public IPage<User> selectPage(UserPageParam param);

    public UserVo login(UserLoginParam param);

    public Long register(UserLoginParam user);
}
