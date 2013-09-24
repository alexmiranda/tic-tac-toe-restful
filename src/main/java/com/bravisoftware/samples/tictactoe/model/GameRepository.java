package com.bravisoftware.samples.tictactoe.model;

public interface GameRepository {
	Game retrieve(Long id);
	void register(Game game);
}
