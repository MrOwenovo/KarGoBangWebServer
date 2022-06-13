package Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ErrorController {

//    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public String error(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("e", e);
        return "500";
    }
}
