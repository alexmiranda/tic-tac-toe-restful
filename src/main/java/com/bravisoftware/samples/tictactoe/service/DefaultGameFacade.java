package com.bravisoftware.samples.tictactoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;
import com.bravisoftware.samples.tictactoe.model.GameRepository;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Position;

@Service
public class DefaultGameFacade implements GameFacade {

	@Autowired
	private GameFactory factory;
	
	@Autowired
	private GameRepository repository;
	
	@Override
	public Long createNewGame() {
		Game game = factory.newGame();
		repository.register(game);
		return game.getId();
	}

	@Override
	public Game loadGame(Long id) {
		return repository.retrieve(id);
	}

	@Override
	public void play(Long gameId, Position position, Mark mark) {
		
	}

	@Override
	public void undoLastMove(Long gameId) {
		
	}

}
