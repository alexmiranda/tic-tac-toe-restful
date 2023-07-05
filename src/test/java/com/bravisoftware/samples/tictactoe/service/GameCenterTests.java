package com.bravisoftware.samples.tictactoe.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;
import com.bravisoftware.samples.tictactoe.model.GameRepository;

@ExtendWith(MockitoExtension.class)
public class GameCenterTests {

	private static final long GAME_ID = 1L;
	private static final int ONCE = 1;

	@Mock
	private GameFactory factory;
	
	@Mock
	private GameRepository repository;
	
	@InjectMocks
	private GameCenter gameCenter;
	
	private Game game;
	
	@BeforeEach
	public void setup() {
		game = new Game(GAME_ID);
		when(factory.newGame()).thenReturn(game);
		when(repository.retrieve(anyLong())).thenReturn(game);
	}

	@Test
	void create_new_game_should_create_game_register_it_and_return_id() throws Exception {
		Long gameId = gameCenter.createNewGame();
		verify(factory, times(ONCE)).newGame();
		verify(repository, times(ONCE)).register(same(game));
		assertThat(gameId, equalTo(GAME_ID));
	}

	@Test
	void load_game_should_retrieve_game_from_repository_with_proper_id() throws Exception {
		Game result = gameCenter.loadGame(GAME_ID);
		verify(repository, times(ONCE)).retrieve(eq(GAME_ID));
		assertThat(result, sameInstance(this.game));
	}
}
