package com.lucy.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @TableName user
 */
@Data
@TableName("user")
public class User implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    @TableField(value = "id")
    private Long id;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @Length(max= 50,message="编码长度不能超过50")
    @TableField(value = "username")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @Length(max= 50,message="编码长度不能超过50")
    @TableField(value = "password")
    @JsonIgnore
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @Length(max= 50,message="编码长度不能超过50")
    @TableField(value = "nickname")
    private String nickname;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @Length(max= 255,message="编码长度不能超过255")
    @TableField(value = "avatar_url")
    private String avatarUrl;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Length(max= 50,message="编码长度不能超过50")
    @TableField(value = "email")
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty("电话")
    @Length(max= 20,message="编码长度不能超过20")
    @TableField(value = "phone")
    private String phone;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    @Length(max= 255,message="编码长度不能超过255")
    @TableField(value = "address")
    private String address;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private Date createTime;

}
