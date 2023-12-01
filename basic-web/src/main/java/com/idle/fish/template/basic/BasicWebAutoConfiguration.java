package com.idle.fish.template.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author idle fish
 * @since 2023/12/1
 */
@Slf4j
@Configuration
@ComponentScan("com.idle.fish.template.basic.web")
public class BasicWebAutoConfiguration {
    public BasicWebAutoConfiguration() {
        log.info("{} init", this.getClass());
    }
}
