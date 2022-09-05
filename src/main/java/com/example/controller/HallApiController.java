package com.example.controller;

import com.example.Timer.ServletContextTimer;
import com.example.entity.data.HallInfoDetail;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.HallService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 大厅信息的相关接口
 */
@Slf4j
@Api(tags = "大厅信息", description = "大厅信息，用户玩家从大厅加入队伍")
@RestController
@RequestMapping("/api/hall")
public class HallApiController {


    @Resource
    ServletContextTimer servletContextTimer;
    @Resource
    HallService hallService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "创建房间成功"),
            @ApiResponse(code = 400, message = "房间已经存在"),
            @ApiResponse(code = 400, message = "错误"),
    })
    @ApiOperation(value = "初始化大厅", notes = "将所有在房间的房间号等信息返回")
    @GetMapping("/getAllRooms")
    public RestBean<HallInfoDetail[]> initHall(HttpServletRequest request) {
        HallInfoDetail[] hallInfo = hallService.getHallInfo();
        return RestBeanBuilder.<HallInfoDetail[]>builder().code(ResultCode.SUCCESS).data(hallInfo).build().ToRestBean();

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "创建房间成功"),
            @ApiResponse(code = 400, message = "错误"),
    })
    @ApiOperation(value = "更新过期的房间号", notes = "点击过期房间后，更新过期的房间号")
    @GetMapping("/reFreshHall")
    public RestBean<Object> reFreshHall(HttpServletRequest request) {
        servletContextTimer.reFreshRoomNumber();
        return RestBeanBuilder.builder().code(ResultCode.SUCCESS).build().ToRestBean();

    }


}
