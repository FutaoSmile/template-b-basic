package com.idle.fish.template.basic;

import com.idle.fish.template.basic.login.peoperties.BasicLoginProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author idle fish
 * @since 2023/11/21
 */
@Slf4j
@Configuration
@ComponentScan("com.idle.fish.template.basic.login")
@EnableConfigurationProperties(BasicLoginProperties.class)
public class BasicLoginAutoConfiguration {
    public BasicLoginAutoConfiguration() {
        log.info("{} init", this.getClass());
    }
}
