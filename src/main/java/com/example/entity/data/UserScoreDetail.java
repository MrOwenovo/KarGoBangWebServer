package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ApiModel("用户游戏信息类")
@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserScoreDetail {
    String username;
    int wins;
    int winRate;
    int passNumber;
    int coins;
}
