package com.example.config;

import com.alibaba.fastjson.JSON;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * springSecurity配置
 */
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Resource
    PersistentTokenRepository tokenRepository;

    @Resource
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/swagger**/**", "/webjars/**", "/v2/**", "/doc.html", "/pre/**", "/api/game/**", "/api/chat/**", "/api/user/**", "/error/**","/api/hall/**").permitAll()  //允许swagger放行
                .antMatchers("/api/auth/access-deny", "/api/auth/verify", "/api/auth/register", "/api/auth/logout", "/api/auth/logout-success").permitAll()
                .antMatchers("/api/**", "/api/auth/login-success").authenticated()
                .antMatchers("/api/auth/guest/isGuest", "/api/room/**").hasRole("user")
                .antMatchers("/api/game/**", "/api/chat/**", "/api/user/**","/api/hall/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/api/auth/access-deny")
                .loginProcessingUrl("/api/auth/login")
                .successForwardUrl("/api/auth/login-success")
                .failureForwardUrl("/api/auth/login-failure")
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/api/auth/logout-success")
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .useSecureCookie(true)
                .alwaysRemember(true)
                .tokenRepository(tokenRepository)
                .and()
                .cors()
                .and()
//                .exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint())
//                .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1);
    }

    /**
     * 匿名用户访问无权限资源时的异常
     * @return json对象
     */
    AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            RestBean<Object> bean = RestBean.builder().success(false).code(ResultCode.NO_PERMISSION.getCode()).message(ResultCode.NO_PERMISSION.getMessage()).build();
            response.setContentType("test/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(bean));
        };
    }


    private CorsConfiguration buildConfig() {
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration config = new CorsConfiguration();
        //设置放行哪些原始域，这里设置为所有
        config.addAllowedOriginPattern("*");
//        //你可以单独设置放行哪些原始域，
//        config.addAllowedOrigin("http://localhost:8668");
//        config.setAllowedOrigins(Collections.singletonList("*"));
        //放行哪些原始请求头部信息
//        config.addAllowedHeader(Arrays.asList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        //放行哪些请求方式，*代表所有
        config.setAllowedMethods(Arrays.asList("PUT", "POST", "GET", "OPTIONS", "DELETE", "PATCH"));
        //是否允许发送Cookie,必须要开启，因为我们的JSESSIONNID需要在Cookie中携带
        config.setAllowCredentials(true);
        config.setExposedHeaders(Collections.singletonList("*"));
        return config;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> configure() {
       log.info("CorsFilter跨域过滤器启用");
        // 映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", buildConfig());
        //项目中有多个filter时此处设置改CorsFilter的优先执行顺序
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


}
