package com.bravisoftware.samples.tictactoe.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bravisoftware.samples.tictactoe.config.TestContext;
import com.bravisoftware.samples.tictactoe.config.WebAppContext;
import com.bravisoftware.samples.tictactoe.factory.GameFactory;
import com.bravisoftware.samples.tictactoe.model.Game;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class GameServiceTest {
	
	private GameFactory factory;
	GameService service;
	
	@Before
	public void setUp(){
		factory = new GameFactory();
		service = new GameServiceBase(factory);
	}
	
	@Test
	public void should_create_a_first_game_with_id_one(){
		
		Game game = service.createGame();
		Assert.assertEquals(game.getId(), Long.valueOf(1));
	}
	
	@Test
	public void should_create_a_second_game_with_id_two(){
		
		service.createGame();
		Game game = service.createGame();
		Assert.assertEquals(game.getId(), Long.valueOf(2));
	}
	
	@Test
	public void should_return_correct_game_by_id(){
		Game newGame = service.createGame();
		Game returnedGame = service.getGame(newGame.getId());
		
		Assert.assertEquals(newGame, returnedGame);
	}

}
