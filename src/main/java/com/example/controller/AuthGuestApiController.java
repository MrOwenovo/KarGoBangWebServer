package com.example.controller;

import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import io.swagger.annotations.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "游客用户验证",description = "以游客身份登录，不支持远程联机")
@RestController
@RequestMapping("/api/auth/guest")
public class AuthGuestApiController extends AuthApiController{

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", dataType = "string",dataTypeClass = String.class,example = "user"),
            @ApiImplicitParam(name = "password", paramType = "query", dataType = "string",dataTypeClass = String.class, example = "123456")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功"),
    })
    @ApiOperation(value = "发送游客登录请求",notes = "以游客身份登录")
    @GetMapping("/login")
    @Override
    public RestBean<Object> login(@RequestParam(value = "username",required = false) String username, @RequestParam(value = "password",required = false) String password) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("guest", null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_guest"));
        securityContext.setAuthentication(token);
        return RestBeanBuilder.builder().code(ResultCode.LOGIN_SUCCESS).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功"),
            @ApiResponse(code = 401, message = "没有权限"),
    })
    @ApiOperation(value = "验证游客身份",notes = "如果是游客身份，则拒绝访问远程联机",authorizations = @Authorization("user"))
    @GetMapping("/isGuest")
    public RestBean<Object> isGuest() {
        //让security自动判断是否有权限
        return RestBeanBuilder.builder().code(ResultCode.SUCCESS).build().ToRestBean();
    }

    @ApiIgnore
    @Override
    public RestBean<Object> logout() {
        return super.logout();
    }

    @ApiIgnore
    @Override
    public RestBean<Object> getVerify(String email) {
        return super.getVerify(email);
    }

    @ApiIgnore
    @Override
    public RestBean<Object> register(String username, String password, String email, String code) {
        return super.register(username, password, email, code);
    }
}
