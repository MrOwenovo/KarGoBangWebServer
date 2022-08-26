package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ApiModel("用户信息类")
@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserInfoDetail {
    public static final String USER_SEX_BOY = "男";
    public static final String USER_SEX_GIRL = "女";

    String username;
    String message;
    String icon;
    String sex;
}
