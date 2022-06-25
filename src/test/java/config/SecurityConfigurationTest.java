package config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigurationTest {

    @Test
    public void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  //这里使用SpringSecurity提供的BCryptPasswordEncoder
        System.out.println(encoder.encode("123456"));
    }

}