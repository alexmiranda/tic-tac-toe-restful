package com.bravisoftware.samples.tictactoe.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bravisoftware.samples.tictactoe.service.GameService;
import com.bravisoftware.samples.tictactoe.factory.GameFactory;
import com.bravisoftware.samples.tictactoe.model.Game;

public class GameServiceBase implements GameService {
	
	private GameFactory factory;
	private Map<Long, Game> repository = new HashMap<Long, Game>();

	@Autowired
	public GameServiceBase(GameFactory factory) {
		this.factory = factory;
	}

	@Override
	public Game createGame() {
		Game game = factory.createGame();
		repository.put(game.getId(), game);
		return game;
	}

	@Override
	public Game getGame(Long id) {
		return repository.get(id);
	}

}
