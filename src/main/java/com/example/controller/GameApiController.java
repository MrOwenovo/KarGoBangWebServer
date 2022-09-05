package com.example.controller;

import com.example.config.ApplicationPro;
import com.example.entity.data.ChessDetail;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.service.GameService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(tags = "三维四子棋联机交互api",description = "完成三维四子棋的先手，后手，出棋以及获胜")
//@CrossOrigin(value = {"https://f01-1309918226.file.myqcloud.com","http://localhost:8668","https://41271w5c23.zicp.vip"},maxAge = 1800,allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/api/game")
public class GameApiController {

    @Resource
    GameService gameService;



    @ApiImplicitParams({
            @ApiImplicitParam(name = "X", paramType = "query", required = true, dataType = "int", dataTypeClass = Integer.class,example = "1"),
            @ApiImplicitParam(name = "Y", paramType = "query", required = true, dataType = "int",dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "z", paramType = "query", required = true, dataType = "int",dataTypeClass = Integer.class, example = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "解析白棋行动成功"),
            @ApiResponse(code = 400,message = "解析白棋行动失败"),
    })
    @ApiOperation(value = "白棋移动一次",notes = "白棋子此时移动了一枚棋子，并将坐标信息传入")
    @PostMapping("/whiteMove")
    public RestBean<Object> whiteMove(@RequestBody ChessDetail chessDetail) {
        return gameService.move(chessDetail.getX(),chessDetail.getY(),chessDetail.getZ(),GameService.WHITE)?
                RestBeanBuilder.builder().code(ResultCode.WHITE_MOVE_SUCCESS).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.WHITE_MOVE_FAILURE).build().ToRestBean();
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X", paramType = "query", required = true, dataType = "int", dataTypeClass = Integer.class,example = "1"),
            @ApiImplicitParam(name = "Y", paramType = "query", required = true, dataType = "int",dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "z", paramType = "query", required = true, dataType = "int",dataTypeClass = Integer.class, example = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "解析黑棋行动成功"),
            @ApiResponse(code = 400,message = "解析黑棋行动失败"),
    })
    @ApiOperation(value = "黑棋移动一次",notes = "黑棋子此时移动了一枚棋子，并将坐标信息传入")
    @PostMapping ("/blackMove")
    public RestBean<Object> blackMove(@RequestBody ChessDetail chessDetail) {
        return gameService.move(chessDetail.getX(),chessDetail.getY(),chessDetail.getZ(),GameService.BLACK)?
                RestBeanBuilder.builder().code(ResultCode.BLACK_MOVE_SUCCESS).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.BLACK_MOVE_FAILURE).build().ToRestBean();
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "白棋已经下子"),
            @ApiResponse(code = 400,message = "白棋尚未下子"),
    })
    @ApiOperation(value = "等待白棋下子",notes = "黑棋正在等待白棋下子，每一秒钟核验一次")
    @GetMapping("/waitForWhiteMove")
    public RestBean<ChessDetail> waitForWhiteMove() {
        ChessDetail chessDetail = gameService.waitForWhiteMove();
        return RestBeanBuilder.<ChessDetail>builder().code(ResultCode.WAIT_WHITE_MOVE_SUCCESS).data(chessDetail).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "黑棋已经下子"),
            @ApiResponse(code = 400,message = "黑棋尚未下子"),
    })
    @ApiOperation(value = "等待黑棋下子",notes = "白棋正在等待黑棋下子，每一秒钟核验一次")
    @GetMapping("/waitForBlackMove")
    public RestBean<ChessDetail> waitForBlackMove() {
        ChessDetail chessDetail = gameService.waitForBlackMove();
        return RestBeanBuilder.<ChessDetail>builder().code(ResultCode.WAIT_BLACK_MOVE_SUCCESS).data(chessDetail).build().ToRestBean();
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "对手已经加入对局"),
            @ApiResponse(code = 400,message = "对手尚未加入对局"),
    })
    @ApiOperation(value = "等待黑棋对手连接",notes = "每隔一秒钟，发送一条验证信息，验证对手是否加入了对局")
    @GetMapping ("/whiteWaitForOpponent")
    public RestBean<Object> whiteWaitForOpponent() {
        return gameService.whiteWaitForOpponent()?
                RestBeanBuilder.builder().code(ResultCode.OPPONENT_IS_IN).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.OPPONENT_NOT_IN).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "对手已经加入对局"),
            @ApiResponse(code = 400,message = "对手尚未加入对局"),
    })
    @ApiOperation(value = "等待白棋对手连接",notes = "每隔一秒钟，发送一条验证信息，验证对手是否加入了对局")
    @GetMapping ("/blackWaitForOpponent")
    public RestBean<Object> blackWaitForOpponent() {
        return gameService.blackWaitForOpponent()?
                RestBeanBuilder.builder().code(ResultCode.OPPONENT_IS_IN).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.OPPONENT_NOT_IN).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "信息存入redis成功"),
            @ApiResponse(code = 400,message = "信息存入redis失败"),
    })
    @ApiOperation(value = "白棋已经开始游戏",notes = "将白棋开始游戏的信息存入redis,等待对方接收")
    @GetMapping ("/whiteIsIn")
    public RestBean<Object> whiteIsIn() {
        return gameService.isIn(GameService.WHITE)?
                RestBeanBuilder.builder().code(ResultCode.SUCCESS).build().ToRestBean():
                RestBeanBuilder.builder().code(ResultCode.FAILURE).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "信息存入redis成功"),
            @ApiResponse(code = 400,message = "信息存入redis失败"),
    })
    @ApiOperation(value = "黑棋已经开始游戏",notes = "将黑棋开始游戏的信息存入redis,等待对方接收")
    @GetMapping ("/blackIsIn")
    public RestBean<Object> blackIsIn() {
        return gameService.isIn(GameService.BLACK) ?
                RestBeanBuilder.builder().code(ResultCode.SUCCESS).build().ToRestBean() :
                RestBeanBuilder.builder().code(ResultCode.FAILURE).build().ToRestBean();
    }

    @PostMapping(value = "/pre")
    public void pre(HttpServletResponse response) {  //处理预检请求
        response.addHeader("Content-Type","text/html; charset=utf-8");
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "数据修改成功"),
            @ApiResponse(code = 400,message = "数据修改失败"),
    })
    @ApiOperation(value = "获得胜利",notes = "获得胜利后，修改用户数据库信息")
    @GetMapping ("/winGame")
    public RestBean<Object> winGame() {
        return gameService.gameResult(true) ?
                RestBeanBuilder.builder().code(ResultCode.MODIFY_SUCCESS).build().ToRestBean() :
                RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).build().ToRestBean();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "数据修改成功"),
            @ApiResponse(code = 400,message = "数据修改失败"),
    })
    @ApiOperation(value = "对局失败",notes = "对局失败后，修改用户数据库信息")
    @GetMapping ("/failureGame")
    public RestBean<Object> failureGame() {
        return gameService.gameResult(false) ?
                RestBeanBuilder.builder().code(ResultCode.MODIFY_SUCCESS).build().ToRestBean() :
                RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).build().ToRestBean();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", paramType = "query", required = true, dataType = "int", dataTypeClass = Integer.class,example = "1"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "数据修改成功"),
            @ApiResponse(code = 400,message = "数据修改失败"),
    })
    @ApiOperation(value = "修改熄灯",notes = "熄灯每过一关，检查是否修改数据库")
    @PatchMapping ("/changePassNumber")
    public RestBean<Object> changePassNumber(@RequestBody int number) {
        return gameService.changePassNumber(number) ?
                RestBeanBuilder.builder().code(ResultCode.MODIFY_SUCCESS).build().ToRestBean() :
                RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).build().ToRestBean();
    }


}

