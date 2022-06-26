package dao.impl;

import dao.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SpringSecurity登录用户信息
 */
@Service
public class UserAuthService implements UserDetailsService {

    @Resource
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String password = mapper.getPasswordByUsername(s);  //从数据库中根据用户名获得密码
        System.out.println(s);
        System.out.println(password);
        if (password == null) {
            throw new UsernameNotFoundException("登陆失败，用户名或密码错误!");
        }
        if (s.equals("guest")){
            return User  //这里需要返回UserDetails,SpringSecurity会根据给定信息进行比对
                    .withUsername(s)
                    .password(new BCryptPasswordEncoder().encode(password))  //直接从数据库获取密码
                    .roles("user")  //用户角色
                    .build();
        }else {
            return User  //这里需要返回UserDetails,SpringSecurity会根据给定信息进行比对
                    .withUsername(s)
                    .password(new BCryptPasswordEncoder().encode(password))  //直接从数据库获取密码
                    .roles("admin")  //用户角色
                    .build();
        }
    }
}

