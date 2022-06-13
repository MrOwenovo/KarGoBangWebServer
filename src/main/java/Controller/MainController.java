package Controller;

import com.alibaba.fastjson.JSONObject;
//import dao.RoomAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
@Slf4j
public class MainController {

//    @Resource
//    RoomAction roomAction;

    //打开创建房间页面
    @RequestMapping(value = "/createRoom",method = RequestMethod.GET)
    public ModelAndView createRoom(HttpServletResponse response) {
        response.addCookie(new Cookie("name","wzy"));

        return new ModelAndView("createRoom");
    }

    //提交房间号
    @RequestMapping(value = "/submitRoomNumber",method = RequestMethod.POST)
    public String submitRoomNumber(HttpServletRequest request,HttpServletResponse response, @RequestParam("number") String roomNumber) throws IOException {
        log.info("接收到的前端数据:"+roomNumber);
//        if (roomAction.getRoomNumber(roomNumber)!=null) {
//            roomAction.addRoomNumber(roomNumber,null);
//            return "forward:/room";
//        }else{
//            return "forward:/createRoomFiled";
//        }

        return roomNumber;
    }

    //返回等待界面
    @RequestMapping(value = "/room",method=RequestMethod.POST)
    public ModelAndView room() {

        return new ModelAndView("room");
    }


    //返回创建房间错误信息
    @RequestMapping(value = "/createRoomFiled",method=RequestMethod.POST)
    @ResponseBody
    public String createRoomFiled() {
        return "该房间已被创建!";
    }

    //进行加密
    @RequestMapping(value = "/changeSecret",method=RequestMethod.GET)
    public ModelAndView changeSecret() {

        return new ModelAndView("createRoomSecret");
    }

    //取消加密
    @RequestMapping(value = "/changeSecretInitial",method = RequestMethod.GET)
    public ModelAndView changeSecretInitial() {

        return new ModelAndView("createRoom");
    }

    //打开加入房间页面
    @RequestMapping(value = "/addRoom",method = RequestMethod.GET)
    public ModelAndView addRoom(HttpServletResponse response) {
        response.addCookie(new Cookie("name","wzy"));

        return new ModelAndView("addRoom");
    }

    //加入房间
    @RequestMapping(value = "/addRoomNumber",method = RequestMethod.POST)
    public String addRoomNumber(HttpServletRequest request,HttpServletResponse response, @RequestParam("number") String roomNumber) throws IOException {
        System.out.println("接收到的前端数据:"+roomNumber);
        return "forward:/room";

    }

    @RequestMapping(value = "/data",produces = "application/json")
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
        System.out.println("接收到的前端数据:"+student);
        return "{\"success\":true}";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam CommonsMultipartFile file)throws IOException {
        File fileObj = new File("error.html");
        file.transferTo(fileObj);
        System.out.println("用户上传的文件已保存到:" + fileObj.getAbsolutePath());
        return "文件上传成功!";
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
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

