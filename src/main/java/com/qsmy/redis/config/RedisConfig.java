package com.qsmy.redis.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
/**
 * 开启缓存
 */
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 配置主键的生成策略KeyGenerator，如不配置，默认使用参数名作为主键
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {

        return (Object target, Method method, Object... params) -> {
            StringBuffer sb = new StringBuffer();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object param : params) {
                sb.append(param.toString());
            }
            return sb.toString();
        };
    }
}
