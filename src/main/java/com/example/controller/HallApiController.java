package com.example.controller;

import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 大厅信息的相关接口
 */
@Slf4j
@Api(tags = "大厅信息", description = "大厅信息，用户玩家从大厅加入队伍")
@RestController
@RequestMapping("/api/hall")
public class HallApiController {


    @ApiResponses({
            @ApiResponse(code = 200, message = "创建房间成功"),
            @ApiResponse(code = 400, message = "房间已经存在"),
            @ApiResponse(code = 400, message = "错误"),
    })
    @ApiOperation(value = "初始化大厅", notes = "将所有在房间的房间号等信息返回")
    @PostMapping("/initHall")
    public RestBean<Object> initHall(HttpServletRequest request) {
//        roomService.createRoom(request, number);
        return RestBeanBuilder.builder().code(ResultCode.CREATE_ROOM_SUCCESS).build().ToRestBean();

    }
}
