package com.example.controller;

import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.AuthService;
import com.example.service.VerifyService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Api(tags = "用户验证", description = "用户登录成功或失败等,含有游客登录")
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Resource
    VerifyService verifyService;
    @Resource
    AuthService authService;

    @ApiIgnore
    @PostMapping("/login-success")
    public RestBean<Object> loginSuccess() {
        return RestBeanBuilder.builder().code(ResultCode.LOGIN_SUCCESS).build().ToRestBean();
    }

    @ApiIgnore
    @PostMapping("/login-failure")
    public RestBean<Object> loginFailure() {
        return RestBeanBuilder.builder().code(ResultCode.LOGIN_FAILURE).build().ToRestBean();
    }

    @ApiIgnore
    @GetMapping("/logout-success")
    public RestBean<Object> logoutSuccess() {
        return RestBeanBuilder.builder().code(ResultCode.LOGOUT_SUCCESS).build().ToRestBean();

    }

    @ApiIgnore
    @RequestMapping("/access-deny")
    public RestBean<Object> accessDeny() {
        return RestBeanBuilder.builder().code(ResultCode.NO_PERMISSION).build().ToRestBean();

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "user"),
            @ApiImplicitParam(name = "password", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "123456")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功"),
            @ApiResponse(code = 400, message = "账号或密码错误,请重新输入")
    })
    @ApiOperation(value = "发起登录请求", notes = "使用springSecurity自带登录")
    @PostMapping("/login")
    public RestBean<Object> login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password) {
        return null;
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "获取验证码成功"),
    })
    @ApiOperation(value = "发起登出请求", notes = "使用springSecurity自带登出")
    @PostMapping("/logout")
    public RestBean<Object> logout() {
        return null;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "user"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求验证码成功"),
            @ApiResponse(code = 400, message = "请求验证码失败,请查看日志")
    })
    @ApiOperation(value = "发起验证码请求", notes = "获取验证码，发送到对应邮箱")
    @PostMapping("/verify")
    public RestBean<Object> getVerify(@RequestParam(value = "email") String email) {
        return verifyService.sendVerifyCode(email) ?
                RestBeanBuilder.builder().code(ResultCode.VERIFY_SUCCESS).build().ToRestBean() :
                RestBeanBuilder.builder().code(ResultCode.VERIFY_FAILURE).build().ToRestBean();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", required = true, dataType = "string",dataTypeClass = String.class, example = "user"),
            @ApiImplicitParam(name = "password", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "123456"),
            @ApiImplicitParam(name = "email", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "lmq122677@qq.com"),
            @ApiImplicitParam(name = "code", paramType = "query", required = true, dataType = "string", dataTypeClass = String.class,example = "123456"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "注册成功"),
            @ApiResponse(code = 400, message = "注册失败,请查看日志"),
            @ApiResponse(code = 401, message = "该用户已经存在")
    })
    @ApiOperation(value = "发起注册请求", notes = "根据用户邮箱和验证码进行注册")
    @PostMapping("/register")
    public RestBean<Object> register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email, @RequestParam(value = "code") String code) {
        //先进行验证码核验
        if (!verifyService.doVerify(email, code))
            return RestBeanBuilder.builder().code(ResultCode.VERIFY_WRONG).build().ToRestBean();
        //进行注册判断
        return authService.register(username, password) ?
                RestBeanBuilder.builder().code(ResultCode.REGISTER_SUCCESS).build().ToRestBean() :
                RestBeanBuilder.builder().code(ResultCode.REGISTER_FAILURE).build().ToRestBean();
    }

}

