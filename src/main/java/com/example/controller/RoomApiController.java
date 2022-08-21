package com.example.controller;

import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "房间验证",description = "用于远程联机的房间验证")
@RestController
@RequestMapping("/api/room")
public class RoomApiController {

    @Resource
    RoomService roomService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class,example = "user"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建房间成功"),
            @ApiResponse(code = 400,message = "房间已经存在"),
            @ApiResponse(code = 400,message = "错误"),
    })
    @ApiOperation(value = "发起创建房间请求",notes = "用房间号创建房间，无加密号")
    @PostMapping("/createRoom")
    public RestBean<Object> createRoom(HttpServletRequest request, @RequestParam(value = "number") String number) {
        roomService.createRoom(request,number);
        return RestBeanBuilder.builder().code(ResultCode.CREATE_ROOM_SUCCESS).build().ToRestBean();

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "user"),
            @ApiImplicitParam(name = "password", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "123456")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建房间成功"),
            @ApiResponse(code = 400,message = "房间已经存在"),
            @ApiResponse(code = 400,message = "错误"),
    })
    @ApiOperation(value = "发起创建加密房间请求",notes = "用房间号和加密号创建房间")
    @PostMapping("/createRoomSecret")
    public RestBean<Object> createRoomSecret(HttpServletRequest request,@RequestParam(value = "number") String number,@RequestParam("password") String password) {
        roomService.createRoomSecret(request,number,password);
        return RestBeanBuilder.builder().code(ResultCode.CREATE_ROOM_SUCCESS).build().ToRestBean();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "user"),
            @ApiImplicitParam(name = "password", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "123456")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "加入房间成功"),
            @ApiResponse(code = 400,message = "加入房间失败"),
    })
    @ApiOperation(value = "发起加入房间请求",notes = "用房间号和加密号加入房间")
    @PostMapping("/addRoom")
    public RestBean<Object> addRoom(HttpServletRequest request,@RequestParam(value = "number") String number,@RequestParam("password") String password) {
        return roomService.addRoom(request,number,password)?
         RestBeanBuilder.builder().code(ResultCode.ADD_ROOM_SUCCESS).build().ToRestBean():
         RestBeanBuilder.builder().code(ResultCode.ADD_ROOM_FAILURE).build().ToRestBean();
    }



    @ApiResponses({
            @ApiResponse(code = 200, message = "对方已经进入房间"),
            @ApiResponse(code = 400,message = "正在等待..."),
    })
    @ApiOperation(value = "查看对手是否加入房间",notes = "每隔三秒请求一次，判断对手是否进入房间")
    @GetMapping("/waitForOpponent")
    public RestBean<Object> waitForOpponent() {
        roomService.waitForOpponent();
        return RestBeanBuilder.builder().code(ResultCode.OPPONENT_IS_IN).build().ToRestBean();

    }
}
