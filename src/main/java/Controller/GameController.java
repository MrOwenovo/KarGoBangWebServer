package Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
@RequestMapping(value = "", produces = { "text/html;charset=UTF-8;", "application/json;charset=UTF-8;" })
@CrossOrigin(origins = "http://localhost:8668", maxAge = 3600)
public class GameController {

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

    @RequestMapping(value = "/move", method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public Object Move(@RequestBody String map) {
        System.out.println("x: "+map);
        return JSON.parse("1");
    }

    @RequestMapping(value = "/pre",method = RequestMethod.POST)
    public void pre(HttpServletResponse response) {  //处理预检请求
//        response.addHeader("Access-Control-Allow-Origin","http://localhost:8668");
//        response.addHeader(" Access-Control-Allow-Credentials","true");
        response.addHeader("Content-Type","text/html; charset=utf-8");
//        return "";
    }

}
