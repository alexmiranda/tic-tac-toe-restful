package com.bravisoftware.samples.tictactoe.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameRepository;

@Component
public class InMemoryGameRepository implements GameRepository {
	
	private Map<Long, Game> games = new ConcurrentHashMap<Long, Game>();

	@Override
	public Game retrieve(Long id) {
		return games.get(id);
	}

	@Override
	public void register(Game game) {
		// TODO Auto-generated method stub
		
	}

}
