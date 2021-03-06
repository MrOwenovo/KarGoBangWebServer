package Controller;

import com.alibaba.fastjson.JSONObject;
import dao.RoomAction;
import entity.AuthUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.CreateRoomService;
import service.MoveService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;


@Controller
@Slf4j
public class RoomController {

    @Resource
    CreateRoomService createRoomService;

    @Resource
    RoomAction roomAction;

    @Resource
    MoveService moveService;

    //打开创建房间页面
    @RequestMapping(value = "/createRoom", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('admin')")
    public String createRoom(HttpServletResponse response) {
        response.addCookie(new Cookie("name", "wzy"));

        return "createRoom";
    }

    //提交房间号
    @RequestMapping(value = "/submitRoomNumber", method = RequestMethod.POST)
    public String submitRoomNumber(HttpServletRequest request, HttpServletResponse response, @RequestParam("number") String roomNumber) throws IOException {
        log.info("接收到的前端数据:" + roomNumber);
        System.out.println(roomAction.getRoomNumber(roomNumber));
        if (roomAction.getRoomNumber(roomNumber) == null) {
            roomAction.addRoomNumber(roomNumber, "");
            response.addCookie(new Cookie("roomNumber", roomNumber));
            return "forward:/room";
        } else {
            System.out.println("创建失败了");
            return "forward:/createRoomFiled";
        }
    }

    //提交房间号和加密号
    @RequestMapping(value = "/submitRoomSecretNumber", method = RequestMethod.POST)
    public String submitRoomSecretNumber(HttpServletRequest request, HttpServletResponse response, @RequestParam("number") String roomNumber, @RequestParam("password") String password) throws IOException {
        log.info("接收到的前端数据:" + roomNumber + "  " + password);
        response.addCookie(new Cookie("roomNumber", roomNumber));
        response.addCookie(new Cookie("password", password));
        System.out.println(roomAction.getRoomNumber(roomNumber));
        if (roomAction.getRoomNumber(roomNumber) == null) {
            roomAction.addRoomNumber(roomNumber, password);
            response.addCookie(new Cookie("roomNumber", roomNumber));
            return "forward:/room";
        } else {
            System.out.println("创建失败了");
            return "forward:/createRoomSecretFiled";
        }
    }

    //返回等待界面
    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public String room(HttpServletResponse response) {
//        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        response.addCookie(new Cookie("account", principal.));
//        if (principal instanceof Principal) {
//            response.addCookie(new Cookie("auth",((Principal) principal).getName()));
//        }
        return "room";
    }

    //查看是否通过
    @RequestMapping(value = "/updateIsReady", method = RequestMethod.GET)
    public String updateIsReady(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String roomNumber = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("roomNumber")) {
                roomNumber = cookie.getValue();
            }
        }
        System.out.println("roomNumber" + roomNumber);
        if (roomAction.getRoomNumber(roomNumber) != null && roomAction.getRoomNumber(roomNumber).getIsReady().equals("1")) {
            roomAction.deleteRoomNumber(roomNumber);
            return "RoomFirstPerson";
        }
        return "false";
    }

    @SneakyThrows
    @RequestMapping(value = "/preLogout",method = RequestMethod.GET)
    public String preLogout() {  //退出登录
        System.out.println("loginout");
        Thread.sleep(3000);
        return "login";
    }

    //返回创建房间错误信息
    @RequestMapping(value = "/createRoomFiled", method = RequestMethod.POST)
    public String createRoomFiled() {
        return "createRoomError";
    }

    //返回创建房间错误信息，带加密号
    @RequestMapping(value = "/createRoomSecretFiled", method = RequestMethod.POST)
    public String createRoomSecretFiled() {
        return "createRoomSecretError";
    }

    //进行加密
    @RequestMapping(value = "/changeSecret", method = RequestMethod.GET)
    public ModelAndView changeSecret() {

        return new ModelAndView("createRoomSecret");
    }

    //取消加密
    @RequestMapping(value = "/changeSecretInitial", method = RequestMethod.GET)
    public ModelAndView changeSecretInitial() {

        return new ModelAndView("createRoom");
    }

    //打开加入房间页面
    @RequestMapping(value = "/addRoom", method = RequestMethod.GET)
    public ModelAndView addRoom(HttpServletResponse response) {
        return new ModelAndView("addRoom");
    }

    //加入房间
    @RequestMapping(value = "/addRoomNumber", method = RequestMethod.POST)
    public String addRoomNumber(HttpServletRequest request, HttpServletResponse response, @RequestParam("number") String roomNumber, @RequestParam("password") String password) throws IOException {
        log.info("接收到的前端数据:" + roomNumber);
        System.out.println(roomNumber);
        System.out.println(roomAction.getRoomNumber(roomNumber));
        if (roomAction.getRoomNumber(roomNumber) != null) {  //如果找到了该房间
            if (roomAction.getRoomNumber(roomNumber).getPassword().equals(password)) {  //密码正确
                roomAction.updateIsReady(roomNumber);  //修改状态
                response.addCookie(new Cookie("roomNumber", roomNumber));
//                AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                response.addCookie(new Cookie("account", principal.getUsername()));
//                if (principal instanceof Principal) {
//                    response.addCookie(new Cookie("auth", ((Principal) principal).getName()));
//                }
                System.out.println("准备创建表");
//                createRoomService.createRoom(roomNumber);
//                System.out.println(i);
                System.out.println("创建表后位置");
                return "RoomFirstPerson";
            } else {
                System.out.println("密码错误："+roomAction.getRoomNumber(roomNumber).getPassword());
                return "addRoomPasswordError";
            }
        } else {
            System.out.println("未找到房间");
            return "addRoomNumberError";
        }
    }


    @RequestMapping(value = "/data", produces = "application/json")
    @ResponseBody
    public JSONObject data() {
        JSONObject student = new JSONObject();
        student.put("name", "杰哥");
        student.put("age", 18);
//        return JSON.toJSONString(student);
        return student;
    }

    @RequestMapping("/submit")
    @ResponseBody
    public String submit(@RequestBody JSONObject student) {
        System.out.println("接收到的前端数据:" + student);
        return "{\"success\":true}";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam CommonsMultipartFile file) throws IOException {
        File fileObj = new File("error.html");
        file.transferTo(fileObj);
        System.out.println("用户上传的文件已保存到:" + fileObj.getAbsolutePath());
        return "文件上传成功!";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response) {
        response.setContentType("multipart/form-data");
        try (OutputStream stream = response.getOutputStream();
             InputStream InputStream = new FileInputStream("error.html")) {
            IOUtils.copy(InputStream, stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
//    @RequestMapping("/index")
//    public String index(Model model) {
//        model.addAttribute("name", "yyds");
//        return "index";
//    }

