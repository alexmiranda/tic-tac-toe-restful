package com.bravisoftware.samples.tictactoe.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bravisoftware.samples.tictactoe.component.DefaultGameFactory;
import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;

public class DefaultGameFactoryTests {
	
	private GameFactory factory;
	
	@Before
	public void setUp() {
		factory = new DefaultGameFactory();
	}
	
	@Test
	public void should_create_a_first_game_with_id_one() {
		Game game = factory.newGame();
		Assert.assertEquals(game.getId(), Long.valueOf(1));
	}
	
	@Test
	public void should_create_a_second_game_with_id_two() {
		factory.newGame();
		Game game = factory.newGame();
		Assert.assertEquals(game.getId(), Long.valueOf(2));
	}
}
