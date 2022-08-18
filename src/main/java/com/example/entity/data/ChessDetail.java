package com.example.entity.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ApiModel("棋子移动信息类")
@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ChessDetail {

    @ApiModelProperty("X坐标")
    int x;
    @ApiModelProperty("Y坐标")
    int y;
    @ApiModelProperty("Z坐标")
    int z;


}
