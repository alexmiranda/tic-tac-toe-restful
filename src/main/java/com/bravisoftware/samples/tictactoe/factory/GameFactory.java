package com.bravisoftware.samples.tictactoe.factory;

import com.bravisoftware.samples.tictactoe.model.Game;


public class GameFactory {
	
	private Long lastId = Long.valueOf(0);
	
	public Game createGame(){
		return createGame(nextId());
	}
	
	protected Game createGame(Long nextId){
		return new Game(nextId);
	}
	
	protected Long nextId() {
		return ++lastId;
	}

}
