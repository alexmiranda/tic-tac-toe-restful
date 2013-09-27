package com.bravisoftware.samples.tictactoe.component;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameRepository;

@Component
public class InMemoryGameRepository implements GameRepository {
	
	private ConcurrentHashMap<Long, Game> games = new ConcurrentHashMap<Long, Game>();

	@Override
	public Game retrieve(Long id) {
		return games.get(id);
	}

	@Override
	public void register(Game game) {
		games.put(game.getId(), game);
	}

}
