package Initializer;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    //会自动注册一个Filter,SpringSecurity底层依靠N个过滤器实现
}
