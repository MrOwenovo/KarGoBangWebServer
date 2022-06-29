package Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.Move;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.MoveService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
@RequestMapping(value = "", produces = { "text/html;charset=UTF-8;", "application/json;charset=UTF-8;" })
@CrossOrigin(origins = "http://localhost:8668", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:8668", maxAge = 3600)
@Slf4j
public class GameController {

    @Resource
    MoveService moveService;

    @PreAuthorize("hasAnyRole('user','admin')")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String select() {
        return "select";
    }

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @RequestMapping(value = "/selectAI", method = RequestMethod.GET)
    public String selectAI() {
        return "selectAI";
    }

    @RequestMapping(value = "/selectModel", method = RequestMethod.GET)
    public String selectModel() {
        return "selectModel";
    }

    @SneakyThrows
    @RequestMapping(value = "/whiteMove", method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public Object whiteMove(@RequestBody Move move, HttpServletRequest request) {
        log.info("白棋走了："+move);
        Cookie[] cookies = request.getCookies();
//        String roomNumber = null;
//        int movesAmount = 0;  //走的总步数
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("roomNumber")) {
//                roomNumber = cookie.getValue();
////                moveService.whiteMove(cookie.getValue(),move.getX(),move.getY(),move.getZ());
//                Move[] moves = moveService.getMoves(roomNumber);//获取一遍走的步数
//                movesAmount = moves.length; //赋值
//            }
//        }
//        while (true) {
//            Thread.sleep(1000);
//            Move[] moves = moveService.getMoves(roomNumber);
//            if (moves.length > movesAmount) {
//                return moves[moves.length - 1];
//            }
//
//        }
        return "{\"" + "x\":" + 1 + ",\"y\":" + 1 + ",\"z\":" + 0 + ",\"type\":" + "\"black\"" + "}";
//        return new Move("1","1","0","black");
    }

    @SneakyThrows
    @RequestMapping(value = "/responseWhite", method = RequestMethod.GET,produces = {"application/json"})
    @ResponseBody
    public Object responseToWhiteMove(HttpServletRequest request) {
        log.info("白棋等待接收...");
        Cookie[] cookies = request.getCookies();
//        String roomNumber = null;
//        int movesAmount = 0;  //走的总步数
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("roomNumber")) {
//                roomNumber = cookie.getValue();
////                moveService.whiteMove(cookie.getValue(),move.getX(),move.getY(),move.getZ());
//                Move[] moves = moveService.getMoves(roomNumber);//获取一遍走的步数
//                movesAmount = moves.length; //赋值
//            }
//        }
//        while (true) {
//            Thread.sleep(1000);
//            Move[] moves = moveService.getMoves(roomNumber);
//            if (moves.length > movesAmount) {
//                return moves[moves.length - 1];
//            }
//
//        }
        return "{\"" + "x\":" + 1 + ",\"y\":" + 1 + ",\"z\":" + 0 + ",\"type\":" + "\"black\"" + "}";
//        return new Move("1","1","0","black");
    }

    @SneakyThrows
    @RequestMapping(value = "/blackMove", method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public Object blackMove(@RequestBody Move move, HttpServletRequest request) {
        log.info("黑棋走了："+move);
        Cookie[] cookies = request.getCookies();
        String roomNumber = null;
        int movesAmount = 0;  //走的总步数
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("roomNumber")) {
                roomNumber = cookie.getValue();
                moveService.blackMove(cookie.getValue(),move.getX(),move.getY(),move.getZ());
                Move[] moves = moveService.getMoves(roomNumber);//获取一遍走的步数
                movesAmount = moves.length; //赋值
            }
        }
        while (true) {
            Thread.sleep(1000);
            Move[] moves = moveService.getMoves(roomNumber);
            if (moves.length > movesAmount) {
                return moves[moves.length - 1];
            }

        }
//        return JSON.parse("1");
    }

    @SneakyThrows
    @RequestMapping(value = "/wait", method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public Object wait(@RequestBody String none, HttpServletRequest request) {
        log.info("黑棋在等待...：");
        Cookie[] cookies = request.getCookies();
        String roomNumber = null;
        int movesAmount = 0;  //走的总步数
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("roomNumber")) {
                roomNumber = cookie.getValue();
                Move[] moves = moveService.getMoves(roomNumber);//获取一遍走的步数
                movesAmount = moves.length; //赋值
            }
        }
        while (true) {
            Thread.sleep(1000);
            Move[] moves = moveService.getMoves(roomNumber);
            if (moves.length > movesAmount) {
                return moves[moves.length - 1];
            }

        }
//        return JSON.parse("1");
    }

    @RequestMapping(value = "/pre",method = RequestMethod.POST)
    public void pre(HttpServletResponse response) {  //处理预检请求
        response.addHeader("Content-Type","text/html; charset=utf-8");
    }

}
