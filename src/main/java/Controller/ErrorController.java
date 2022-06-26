package Controller;

import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return "error";
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String handlerException(){
        return "redirect:/error";
    }


}
