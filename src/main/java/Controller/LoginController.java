package Controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value = "/login/{account}/{password}",method = RequestMethod.GET)
    public String login(@PathVariable("account")String account,@PathVariable("password")String password) {//强制登录
        SecurityContext context = SecurityContextHolder.getContext(); //获取SecurityContext对象（当前会话肯定是没有登陆的）
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));  //手动创建一个UsernamePasswordAuthenticationToken对象，也就是用户的认证信息，角色需要添加ROLE_前缀，权限直接写
        context.setAuthentication(token);  //手动为SecurityContext设定认证信息
        return "login";
    }


    @RequestMapping("/logout")
    public String logout() {
        System.out.println("退出登录");
        return "";
    }
}
