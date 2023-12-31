package com.lucy.springboot.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户管理列表查询参数")
public class UserPageParam {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email="";

    @ApiModelProperty(value = "地址")
    private String address;

    private PageParam pageParam=new PageParam();
}
