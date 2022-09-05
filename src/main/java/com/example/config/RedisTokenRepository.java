package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * springSecurity RememberMe的token 储存在redis
 */
@Slf4j
@Configuration
public class RedisTokenRepository implements PersistentTokenRepository {

    private final static String REMEMBER_ME_KEY = "spring:security:rememberMe:";

    @Resource
    RedisTemplate<Object, Object> tokenTemplate;

    @PostConstruct
    private void init() {
        log.info("存储Spring-RememberMe缓存的Redis配置加载");;
    }

    //由于PersistentRememberMeToken没有实现序列化接口，这里用Map存放
    private PersistentRememberMeToken getToken(String series) {
        Map<Object, Object> map = tokenTemplate.opsForHash().entries(series);
        if (map.isEmpty()) return null;
        Object date = map.get("date");
        if (date==null) return null;
        return new PersistentRememberMeToken(
                (String) map.get("username"),
                (String) map.get("series"),
                (String) map.get("tokenValue"),
                new Date(Long.parseLong((String) map.get("date")))
        );
    }

    private void setTokenTemplate(PersistentRememberMeToken token) {
        Map<Object, Object> map = new HashMap<>();
        map.put("username", token.getUsername());
        map.put("series", token.getSeries());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", token.getDate().getTime());
        tokenTemplate.opsForHash().putAll(REMEMBER_ME_KEY + map.get("series"), map);
        tokenTemplate.expire(REMEMBER_ME_KEY + map.get("series"), 1, TimeUnit.DAYS);
    }


    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenTemplate.opsForValue().set(REMEMBER_ME_KEY + "name:" + token.getUsername(), token.getSeries());
        tokenTemplate.expire(REMEMBER_ME_KEY + "name:" + token.getUsername(), 1, TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = getToken(series);
        if (token != null)
            this.setTokenTemplate(new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return getToken(seriesId);
    }

    //通过username找series直接删两个
    @Override
    public void removeUserTokens(String username) {
         String series= (String) tokenTemplate.opsForValue().get(REMEMBER_ME_KEY + "name:" + username);
        tokenTemplate.delete(REMEMBER_ME_KEY + "name:" + username);
        tokenTemplate.delete(REMEMBER_ME_KEY + series);

    }
}
