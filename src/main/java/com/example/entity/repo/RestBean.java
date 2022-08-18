package com.example.entity.repo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ApiModel("响应实体封装类")
public class RestBean<T> {

    @ApiModelProperty("是否成功")
    @NonNull boolean success;

    @ApiModelProperty("状态码")
    @NonNull int code;

    @ApiModelProperty("状态码描述信息")
    @NonNull String message;

    @ApiModelProperty("响应实体数据")
    T data ;

}
