package com.bravisoftware.samples.tictactoe.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bravisoftware.samples.tictactoe.component.DefaultGameFactory;
import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;

public class DefaultGameFactoryTests {
	
	private GameFactory factory;
	
	@BeforeEach
	public void setUp() {
		factory = new DefaultGameFactory();
	}

	@Test
	void should_create_a_first_game_with_id_one() {
		Game game = factory.newGame();
		Assertions.assertEquals(game.getId(), Long.valueOf(1));
	}

	@Test
	void should_create_a_second_game_with_id_two() {
		factory.newGame();
		Game game = factory.newGame();
		Assertions.assertEquals(game.getId(), Long.valueOf(2));
	}
}
