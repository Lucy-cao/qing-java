package com.lucy.springboot.config;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceConfig {
    @Bean
    public Sequence sequence() {
        return new Sequence(1, 1);
    }
}
