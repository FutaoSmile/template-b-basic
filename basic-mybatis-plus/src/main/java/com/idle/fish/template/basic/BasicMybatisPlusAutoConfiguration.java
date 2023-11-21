package com.idle.fish.template.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author idle fish
 * @since 2023/11/21
 */
@Slf4j
@Configuration
@ComponentScan("com.idle.fish.template.basic.mybatis.plus")
public class BasicMybatisPlusAutoConfiguration {
    public BasicMybatisPlusAutoConfiguration() {
        log.info("{} init", this.getClass());
    }
}
