package com.lucy.springboot.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户登录参数")
public class UserLoginParam {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 确认密码
     */
    @ApiModelProperty("确认密码")
    private String confirmPassword;
}
