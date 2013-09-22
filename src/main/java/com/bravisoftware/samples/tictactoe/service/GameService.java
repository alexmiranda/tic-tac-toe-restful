package com.bravisoftware.samples.tictactoe.service;

import com.bravisoftware.samples.tictactoe.model.Game;

public interface GameService {
	
	Game createGame();

	Game getGame(Long id);

}
