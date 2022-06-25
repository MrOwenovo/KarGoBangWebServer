package config;

import dao.impl.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    UserAuthService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(service)  //使用自定义的Service实现类进行验证
                .passwordEncoder(new BCryptPasswordEncoder());

//        auth.inMemoryAuthentication()  //直接验证方式，之后会有数据库验证
//                .passwordEncoder(encoder)  //密码加密器
//                .withUser("root")  //用户名
//                .password(encoder.encode("123123"))  //加密后的密码
//                .roles("user");  //用户的角色
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()  //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**").permitAll()  //静态资源，使用permitAll来让任何人访问
//                .antMatchers("/login").hasAnyRole("user", "admin")  //所有请求必须登录并且是user角色才可以访问
                .anyRequest().hasRole("admin")  //所有请求必须登录并且是user角色才可以访问

//                .antMatchers("/**").hasRole("user")  //所有请求必须登录并且是user角色才可以访问
                .and()
                .formLogin()  //配置Form表单登录
                .loginPage("/login")  //登陆页面地址(GET)
                .loginProcessingUrl("/doLogin")  //from表单提交地址POST
                .defaultSuccessUrl("/select",true)  //登陆成功后跳转的页面，也可以通过Handler实现高度自定义
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        log.info("登录失败");
                        httpServletResponse.sendRedirect("/login");
                    }
                })
                .permitAll()  //登陆界面允许所有人访问
                .and()
                .logout()
                .logoutUrl("/logout")  //退出登录的请求地址
                .logoutSuccessUrl("/login")  //推出后重定向的地址
                .and()
                .csrf().disable();  //暂时关闭csrf防护
    }
}
