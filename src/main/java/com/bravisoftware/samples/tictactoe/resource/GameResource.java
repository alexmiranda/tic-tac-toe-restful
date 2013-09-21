package com.bravisoftware.samples.tictactoe.resource;

import org.springframework.hateoas.ResourceSupport;

import com.bravisoftware.samples.tictactoe.model.Game;

public class GameResource extends ResourceSupport {
	
	private Game game;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
