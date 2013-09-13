package com.bravisoftware.samples.tictactoe.model;

public class Game {
	
	private Move lastMove = Move.EMPTY;
	private Move [] moves = new Move[9];	

	public void play(Position position, Mark mark) {
		validate(position, mark);
		lastMove = new Move(position, mark);
		moves[position.index()] = lastMove;
	}

	private void validate(Position position, Mark mark) {
		if (isFilled(position)) {
			throw new BadMoveException();
		}
		if (lastMove.samePosition(position) || lastMove.sameMark(mark)) {
			throw new BadMoveException();
		}
	}

	private boolean isFilled(Position position) {
		return moves[position.index()] != null;
	}

	public Move lastMove() {
		return lastMove;
	}

}
