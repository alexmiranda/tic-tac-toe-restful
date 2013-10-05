package com.bravisoftware.samples.tictactoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;
import com.bravisoftware.samples.tictactoe.model.GameNotFoundException;
import com.bravisoftware.samples.tictactoe.model.GameRepository;
import com.bravisoftware.samples.tictactoe.model.GameStatus;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Position;

@Service
public class GameCenter implements GameFacade {

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
		Game game = repository.retrieve(id);
		if (game == null) {
			throw new GameNotFoundException();
		}
		return game;
	}

	@Override
	public void play(Long gameId, Position position, Mark mark) {
		validate(position, mark);
		Game game = this.loadGame(gameId);
		game.play(position, mark);
	}

	@Override
	public void undoLastMove(Long gameId) {
		Game game = this.loadGame(gameId);
		game.undo();
	}
	
	public void changeStatus(Long gameId, GameStatus status) {
		Game game = this.loadGame(gameId);
		game.setStatus(status);
	}
	
	private void validate(Position position, Mark mark) {
		if (position == null) {
			throw new IllegalArgumentException("position");
		}
		if (mark == null) {
			throw new IllegalArgumentException("mark");
		}
	}

}
