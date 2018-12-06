package com.liumapp.qtools.property.config;


import com.liumapp.qtools.property.bean.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * file UserConfig.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 */
@Configuration
public class UserConfig {

    @Bean
    @ConfigurationProperties(prefix = "com.liumapp.qtools.property.user")
    public User user () {
        return new User();
    }

}
