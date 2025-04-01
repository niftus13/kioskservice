package com.cbox.kioskservice.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cbox.kioskservice.api.common.formatter.LocalDateFormatter;



@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        
        registry.addFormatter(new LocalDateFormatter());
    }

    
}
