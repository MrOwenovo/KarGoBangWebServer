package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@ApiModel("用户游戏信息类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScoreDetail implements Serializable {
    String username;
    int wins;
    int winRate;
    int passNumber;
    int coins;
    int user_id;
}
