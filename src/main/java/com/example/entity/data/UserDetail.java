package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@ApiModel("用户信息类")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements Serializable {
    int id;
    String username;
    String password;
    String role;
    UserInfoDetail userInfo;
    UserScoreDetail userScore;
}
