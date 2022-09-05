package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.awt.image.BufferedImage;

@ApiModel("大厅中的房间信息类")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallInfoDetail {
    @ApiModelProperty("房间号")
    String roomNumber;
    @ApiModelProperty("玩家名称")
    String username;
    @ApiModelProperty("玩家头像")
    String image;
    @ApiModelProperty("玩家简介")
    String message;
}
