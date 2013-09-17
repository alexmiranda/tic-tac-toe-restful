package com.bravisoftware.samples.tictactoe.model;

public class Game {
	
	private final Mark [] grid = new Mark[9];
	
	private Move lastMove = Move.EMPTY;
	private int remainingMoves = 9;
	private boolean over;

	public void play(Position position, Mark mark) {
		validate(position, mark);
		lastMove = new Move(position, mark);
		grid[position.index()] = mark;
		remainingMoves--;
		checkWinnerAndChangeStatus();
	}

	private void checkWinnerAndChangeStatus() {
		if (remainingMoves == 0) {
			over = true;
			return;
		}
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
		if (this.isOver()) {
			throw new GameOverException();
		}
		if (isFilled(position)) {
			throw new FilledPositionException();
		}
		if (lastMove.sameMark(mark)) {
			throw new InvalidPlayerException();
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
