package com.app.mangementApp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Project Name - Management Application
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - Amar Sawant
 * @since - 27-05-2024
 */
@Configuration
public class CommonConfigs {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
