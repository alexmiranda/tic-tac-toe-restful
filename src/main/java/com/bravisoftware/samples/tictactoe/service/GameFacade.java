package com.bravisoftware.samples.tictactoe.service;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameStatus;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Position;

public interface GameFacade {
	Long createNewGame();
	Game loadGame(Long id);
	void play(Long gameId, Position position, Mark mark);
	void undoLastMove(Long gameId);
	void changeStatus(Long gameId, GameStatus status);
}
