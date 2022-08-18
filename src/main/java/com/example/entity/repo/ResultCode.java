package com.example.entity.repo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@Getter
public enum ResultCode {

    SUCCESS(200, "成功"),

    LOGIN_SUCCESS(200, "登录成功"),

    LOGOUT_SUCCESS(200, "登出成功"),

    VERIFY_SUCCESS(200, "发送验证码成功"),

    REGISTER_SUCCESS(200, "注册成功"),

    CREATE_ROOM_SUCCESS(200, "创建房间成功"),

    OPPONENT_IS_IN(200, "对手已经加入房间!"),

    ADD_ROOM_SUCCESS(200, "加入房间成功!"),

    WHITE_MOVE_SUCCESS(200, "解析白棋行动成功!"),

    BLACK_MOVE_SUCCESS(200, "解析黑棋行动成功!"),

    WAIT_WHITE_MOVE_SUCCESS(200, "白棋已经下子!"),

    WAIT_BLACK_MOVE_SUCCESS(200, "黑棋已经下子!"),




    FAILURE(400,"错误"),

    NO_PERMISSION(401,"没有权限"),

    LOGIN_FAILURE(402,"账号或密码错误,请重新输入"),

    LOGOUT_FAILURE(403,"登出错误"),

    VERIFY_FAILURE(404, "发送验证码失败"),

    REGISTER_FAILURE(405, "注册失败"),

    ALREADY(406, "数据库中已经存在该信息"),

    NOT_EXIST(412, "数据库中不存在该信息"),

    VERIFY_WRONG(407, "验证码错误!"),

    OPPONENT_NOT_IN(408, "正在等待...!"),

    ADD_ROOM_FAILURE(409, "加入房间失败"),

    WHITE_MOVE_FAILURE(410, "解析白棋行动失败"),

    BLACK_MOVE_FAILURE(411, "解析黑棋行动失败");












    int code;
    String message;


    }
