package com.example.controller;

import com.example.controller.exception.NotExistInMysqlException;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.entity.constant.ThreadDetails;
import com.example.entity.data.UserDetail;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "用户服务", description = "修改用户信息，查询用户信息等")
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Resource
    AuthMapper authMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;


    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "获取失败,请查看日志"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    @ApiOperation(value = "获取用户详细信息", notes = "获取用户的信息，游戏信息等")
    @GetMapping("/getUserDetails")
    public RestBean<UserDetail> getUserDetails() {
        //获取用户
        String name = ThreadDetails.getUsername();
        UserDetail userDetail = authMapper.findUserByUsername(name).orElseThrow(() -> new NotExistInMysqlException("不存在该用户!"));
        return RestBeanBuilder.<UserDetail>builder().code(ResultCode.REGISTER_SUCCESS).data(userDetail).build().ToRestBean();
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", dataType = "string", dataTypeClass = String.class,example = "user"),
            @ApiImplicitParam(name = "message", paramType = "query",  dataType = "string",dataTypeClass = String.class, example = "123456"),
            @ApiImplicitParam(name = "sex", paramType = "query",  dataType = "string",dataTypeClass = String.class, example = "男")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 400, message = "修改失败,请查看日志"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    @ApiOperation(value = "修改某项用户详细信息", notes = "修改某项用户的用户名、简介等")
    @PatchMapping("/modifySomeUserDetails")
    public RestBean<Object> modifySomeUserDetails(@RequestParam(value = "username",required = false) String username,@RequestParam(value = "message", required = false) String message,@RequestParam(value = "sex",required = false) String sex) {
        return userService.modifyUserDetails(username, message,sex)?
                RestBeanBuilder.builder().code(ResultCode.MODIFY_SUCCESS).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).build().ToRestBean();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "user"),
            @ApiImplicitParam(name = "message", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "123456"),
            @ApiImplicitParam(name = "sex", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "男")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 400, message = "修改失败,请查看日志"),
            @ApiResponse(code = 401, message = "没有权限")
    })
    @ApiOperation(value = "修改用户详细信息", notes = "修改用户的用户名、简介等")
    @PutMapping("/modifyUserDetails")
    public RestBean<Object> modifyUserDetails(@RequestParam(value = "username") String username,@RequestParam(value = "message") String message,@RequestParam(value = "sex",required = false) String sex) {
        return userService.modifyUserDetails(username, message,sex)?
                RestBeanBuilder.builder().code(ResultCode.MODIFY_SUCCESS).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).build().ToRestBean();
    }
}
