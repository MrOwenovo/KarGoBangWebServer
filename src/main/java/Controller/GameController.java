package Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameController {

    @PreAuthorize("hasAnyRole('user','admin')")
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public String select() {
        return "select";
    }

    @PreAuthorize("hasAnyAuthority('user','admin')")
    @RequestMapping(value = "/selectAI",method = RequestMethod.GET)
    public String selectAI() {
        return "selectAI";
    }

    @RequestMapping(value = "/selectModel",method = RequestMethod.GET)
    public String selectModel() {
        return "selectModel";
    }



}
