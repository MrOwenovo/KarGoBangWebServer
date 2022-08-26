package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.awt.image.BufferedImage;

@ApiModel("大厅中的房间信息类")
@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class HallInfoDetail {
    @ApiModelProperty("房间号")
    String roomNumber;
    @ApiModelProperty("玩家头像")
    BufferedImage image;
    @ApiModelProperty("玩家简介")
    String message;
}
