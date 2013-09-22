package com.bravisoftware.samples.tictactoe.resource;

import org.springframework.hateoas.ResourceSupport;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameResult;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Move;
import com.bravisoftware.samples.tictactoe.model.Position;

public class GameResource extends ResourceSupport {

	private Game game;
	private MoveDTO lastMove;
	private StatusConverter converter = new StatusConverter();

	public void setGame(Game game) {
		this.game = game;
		this.lastMove = new MoveDTO(game.lastMove());
	}

	public MoveDTO getLastMove() {
		return lastMove;
	}
	
	public String getStatus(){
		return this.converter.status(game.getResult());
	}

	public static class MoveDTO {

		Move move;

		public MoveDTO(Move move) {
			this.move = move;
		}

		public String getMark() {
			return this.move.sameMark(Mark.X) ? "X" : "O";
		}

		public String getPosition() {
			return String.valueOf(this.move.positionIndex());
		}

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
