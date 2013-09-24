package com.bravisoftware.samples.tictactoe.component;

import org.springframework.stereotype.Component;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;

@Component
public class DefaultGameFactory implements GameFactory {
	
	private volatile Long lastId = Long.valueOf(0);
	
	@Override
	public Game newGame() {
		Long nextId = nextId();
		return new Game(nextId);
	}
	
	private synchronized Long nextId() {
		return ++lastId;
	}

}
