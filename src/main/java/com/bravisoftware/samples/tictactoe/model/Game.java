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
		checkHorizontalRows();
		checkVerticalRows();
		checkDiagonalRows();
	}

	private void checkHorizontalRows() {
		for (int i = 0; i < grid.length; i = i + 3) {
			if (check(i, i + 1, i + 2)) {
				break;
			}
		}
	}
	
	private void checkVerticalRows() {
		for (int i = 0; i <= 2; i++) {
			if (check(i, i + 3, i + 6)) {
				break;
			}
		}
	}
	
	private void checkDiagonalRows() {
		check(0, 4, 8);
		check(2, 4, 6);
	}

	private boolean check(int first, int second, int third) {
		if (!isFilled(first) || !isFilled(second) || !isFilled(third)) {
			return false;
		}
		if (grid[first] == grid[second] && grid[second] == grid[third]) {
			over = true;
		}
		return over;
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
		return isFilled(position.index());
	}
	
	private boolean isFilled(int position) {
		return grid[position] != null;
	}

	public Move lastMove() {
		return lastMove;
	}

	public boolean isOver() {
		return over;
	}

}
