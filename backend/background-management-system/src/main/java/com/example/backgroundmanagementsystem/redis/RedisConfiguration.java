package com.example.backgroundmanagementsystem.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    @Value("${spring.data.redis.database}")
    private int dataBase;
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private String password;
    @Value("${spring.data.redis.timeout}")
    private long timeOut;
    @Value("${spring.data.redis.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${spring.data.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.data.redis.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${spring.data.redis.lettuce.pool.max-wait}")
    private int maxWait;

    /**
     * 配置线程池连接工厂LettuceConnectionFactory
     * @return
     */
    /*@Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        // redis配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setDatabase(dataBase);
        redisStandaloneConfiguration.setPassword(password);

        // 连接池配置
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWait(Duration.ofDays(maxWait));
        genericObjectPoolConfig.setMaxTotal(maxActive);

        // ClientOptions配置
        ClientOptions clientOptions = ClientOptions.builder().protocolVersion(ProtocolVersion.RESP2).build();

        // redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder =  LettucePoolingClientConfiguration
                .builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofMillis(timeOut));

        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration,lettuceClientConfiguration);
    }*/

    /**
     * Redis初始化
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // JSON序列化配置
        ObjectMapper objectMapper=new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会抛出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);

        // String的序列化
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();

        //key和hash的key都采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        //value和hash的value都采用jackson的序列化方式
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

}
