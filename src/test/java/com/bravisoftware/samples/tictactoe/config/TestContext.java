package com.bravisoftware.samples.tictactoe.config;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.bravisoftware.samples.tictactoe.component.DefaultGameFactory;
import com.bravisoftware.samples.tictactoe.component.InMemoryGameRepository;
import com.bravisoftware.samples.tictactoe.model.GameFactory;
import com.bravisoftware.samples.tictactoe.model.GameRepository;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;
import com.bravisoftware.samples.tictactoe.service.GameFacade;

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
    
    @Bean
    public GameFactory getGameFactory(){
    	return new DefaultGameFactory();
    }
    
    @Bean
    public GameRepository getGameRepository(){
    	GameRepository repository = new InMemoryGameRepository();
    	return repository;
    }
    
    @Bean
    public GameFacade getGameCenter(){
    	return Mockito.mock(GameFacade.class);
    }

}