package com.bravisoftware.samples.tictactoe.model;

public class Game {
	
	private Move lastMove = Move.EMPTY;
	private Mark [] grid = new Mark[9];	
	private boolean over;

	public void play(Position position, Mark mark) {
		validate(position, mark);
		lastMove = new Move(position, mark);
		grid[position.index()] = mark;
		checkWinnerAndChangeStatus();
	}

	private void checkWinnerAndChangeStatus() {
		for (int i = 0; i < grid.length; i = i + 3) {
			if (grid[i] == grid[i + 1] && grid[i + 1] == grid[i + 2]) {
				over = true;
				break;
			}
		}
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
		return grid[position.index()] != null;
	}

	public Move lastMove() {
		return lastMove;
	}

	public boolean isOver() {
		return over;
	}

}
