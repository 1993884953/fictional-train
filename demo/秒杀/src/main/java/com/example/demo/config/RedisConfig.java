package com.example.demo.config;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * RedisTemplate 默认配置的是使用 Java JDK 的序列化，如果是对象，在Redis里查看时不直观
     * key 使用 String， value 列序列化 json订单
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 连接工厂
        template.setConnectionFactory(factory);

        // key 使用 StringRedisSerializer 来序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // value 采用 json 序列化
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = jsonSerializer();
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 序列化器
     */
    private Jackson2JsonRedisSerializer<Object> jsonSerializer() {

        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 对象映射
        ObjectMapper om = new ObjectMapper();

        // 序列化所有字段和修饰符范围
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 将类名序列化到json中 {"@class":"model.User", "id":"1", "name":"hello"}
        // 否则不能反序列化 {"id":"1", "name":"hello"}
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        jsonRedisSerializer.setObjectMapper(om);

        return jsonRedisSerializer;
    }
}