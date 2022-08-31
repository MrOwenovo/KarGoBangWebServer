package com.example.controller;

import com.example.dao.ChatMapper;
import com.example.entity.data.UserDetail;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.ChatService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api(tags = "用户聊天", description = "用户互相发送消息或通讯")
@RestController
@RequestMapping("/api/chat")
public class ChatApiController {

    @Resource
    ChatService chatService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "123456"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "发送成功"),
            @ApiResponse(code = 400, message = "发送失败,请查看日志"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    @ApiOperation(value = "发送消息", notes = "游戏中发送的游戏信息")
    @PostMapping("/sendMessage")
    public RestBean<Object> sendMessage(HttpServletRequest request, @RequestParam(value = "message") String message) {
        chatService.sendMessage(request, message);
        return RestBeanBuilder.builder().code(ResultCode.SEND_SUCCESS).build().ToRestBean();
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "获取失败,请查看日志"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    @ApiOperation(value = "获取对方消息", notes = "获取游戏中对方发送的游戏信息")
    @GetMapping("/getMessage")
    public RestBean<String> getMessage() {
        String message = chatService.getMessage();
        return RestBeanBuilder.<String>builder().code(ResultCode.SEND_SUCCESS).data(message).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "获取失败,请查看日志"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    @ApiOperation(value = "获取双方的用户信息", notes = "获取自己和对方的所有数据")
    @GetMapping("/getUserDetails")
    public RestBean<List<UserDetail>> getMyAndOpponentUserDetailsInGame() {
        List<UserDetail> myAndOpponentUserDetailsInGame = chatService.getMyAndOpponentUserDetailsInGame();
        return RestBeanBuilder.<List<UserDetail>>builder().code(ResultCode.SEND_SUCCESS).data(myAndOpponentUserDetailsInGame).build().ToRestBean();
    }
}
