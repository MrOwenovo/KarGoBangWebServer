package com.example.controller;

import com.example.controller.exception.NotExistInRedisException;
import com.example.entity.constant.ThreadDetails;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.AuthService;
import com.example.service.impl.RoomServiceImpl;
import com.example.service.util.IpTools;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@Api(tags = "游戏前的预检和登录", description = "进入游戏房间后后台登录等，后期会换成oauth2登录")
@RestController
@RequestMapping("/pre")
public class GamePreController {

    @Resource
    AuthService authService;
    @Resource
    RedisTemplate<Object, Object> template;



    @ApiResponses({
            @ApiResponse(code = 200, message = "后台登录成功"),
            @ApiResponse(code = 400, message = "后台登录失败")
    })
    @ApiOperation(value = "进行后台登录", notes = "从redis中取出账号密码，以usernamePasswordAuthenticationToken形式后台登陆")
    @GetMapping("/preLogin")
    public RestBean<Object> preLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //从redis中获取session
        Object o = template.opsForValue().get(RoomServiceImpl.IP_SESSION_TOKEN_KEY + IpTools.getIpAddress(request));
        System.out.println(RoomServiceImpl.IP_SESSION_TOKEN_KEY + IpTools.getIpAddress(request));
        if (o== null) throw new NotExistInRedisException("Redis中不存在该sessionId");
        String  sessionId = (String) o;
        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        response.addCookie(cookie);
//        //获取验证信息Authentication
//        Object o = authService.getAuthentication().getPrincipal();
//        User principal;
//        if (o instanceof User) {
//            principal = (User) o;
//        } else {
//            throw new Exception("redis中的authentication不是账号密码登录");
//        }
//        //通过UsernamePasswordAuthenticationToken后台登录
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal.getUsername(), new BCryptPasswordEncoder().encode(principal.getPassword()));
//        securityContext.setAuthentication(token);
        return RestBeanBuilder.builder().code(ResultCode.LOGIN_SUCCESS).messageType(RestBeanBuilder.USER_DEFINED).message("后台登陆成功!").build().ToRestBean();
    }
}
