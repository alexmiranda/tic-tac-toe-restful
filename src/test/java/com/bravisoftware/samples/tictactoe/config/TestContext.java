package com.bravisoftware.samples.tictactoe.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;

@Configuration
public class TestContext {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
    
    @Bean
    public GameResourceAssembler getGameResourceAssembler() {
        return new GameResourceAssembler();
    }

}