package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@ApiModel("用户信息类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDetail implements Serializable {
    public static final String USER_SEX_BOY = "男";
    public static final String USER_SEX_GIRL = "女";

    String username;
    String message;
    String icon;
    String sex;
    int user_id;
}
