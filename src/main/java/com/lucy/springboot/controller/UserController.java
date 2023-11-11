package com.lucy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lucy.springboot.domain.common.api.CommonResult;
import com.lucy.springboot.domain.dto.UserLoginParam;
import com.lucy.springboot.domain.dto.UserPageParam;
import com.lucy.springboot.domain.vo.UserVo;
import com.lucy.springboot.entity.User;
import com.lucy.springboot.mapper.UserMapper;
import com.lucy.springboot.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存或编辑用户
     * @param user
     * @return
     */
    @PostMapping
    public CommonResult<Boolean> save(@RequestBody @Validated User user){
        //新增或更新都在此方法内
        return CommonResult.success(userService.saveUser(user));
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping
    public CommonResult<List<User>> index(){
        return CommonResult.success(userService.list());
    }

    @GetMapping("/{id}")
    public CommonResult<User> getById(@PathVariable Long id){
        return CommonResult.success(userService.getById(id));
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResult<Long> delete(@PathVariable Long id){
        userService.removeById(id);
        return CommonResult.success(id);
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @PostMapping("/delBatch")
    public CommonResult<Boolean> delBatch(@RequestBody List<Long> ids){
        return CommonResult.success(userService.removeBatchByIds(ids));
    }

    /**
     * 获取用户分页列表
     * @param param
     * @return
     */
    @PostMapping("page")
    public CommonResult<IPage<User>> selectPage(@RequestBody UserPageParam param){
        return CommonResult.success(userService.selectPage(param));
    }

    @PostMapping("login")
    public CommonResult<UserVo> login(@RequestBody @Validated UserLoginParam param){
        return CommonResult.success(userService.login(param));
    }

    @PostMapping("register")
    public CommonResult<Long> register(@RequestBody @Validated UserLoginParam user){
        return CommonResult.success(userService.register(user));
    }
}
