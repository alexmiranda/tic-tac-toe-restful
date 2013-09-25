package com.bravisoftware.samples.tictactoe.resource;

import org.springframework.hateoas.ResourceSupport;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameResult;
import com.bravisoftware.samples.tictactoe.model.Move;

public class GameResource extends ResourceSupport {

	private Game game;
	private LastMoveResource lastMove;
	private StatusConverter converter = new StatusConverter();

	public void setGame(Game game) {
		this.game = game;
		if (game.lastMove() != Move.EMPTY) {
			this.lastMove = new LastMoveResource(game.lastMove());
		}
	}

	public LastMoveResource getLastMove() {
		return lastMove;
	}
	
	public String getStatus(){
		return this.converter.status(game.getResult());
	}

	public static class StatusConverter {

		protected String status(GameResult gameResult) {
			switch (gameResult) {
			case OPEN:
				return "Open";
			case X_WINS:
				return "X wins";
			case O_WINS:
				return "O wins";
			case DRAW:
				return "Draw";
			default:
				return "Inconclusive";
			}
		}
	}

}
