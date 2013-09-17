package com.bravisoftware.samples.tictactoe.model;

import java.util.Stack;

public class Game {
	
	private static final Mark BLANK = null;

	private final Mark [] grid = new Mark[9];
	
	private Stack<Move> moves = new Stack<Move>();
	private boolean over;
	private GameResult result = GameResult.OPEN;

	public void play(Position position, Mark mark) {
		validate(position, mark);
		Move move = new Move(position, mark);
		grid[position.index()] = mark;
		moves.push(move);
		checkWinnerAndChangeStatus();
	}

	private void checkWinnerAndChangeStatus() {
		if (threeInARow()) {
			determineWinner();
			return;
		}
		drawGameIfThereIsNoBlankPositionLeft();
	}

	private boolean threeInARow() {
		return checkHorizontalRows() || checkVerticalRows() || checkDiagonalRows();
	}
	
	private void determineWinner() {
		result = lastMove().sameMark(Mark.X) ? GameResult.X_WINS : GameResult.O_WINS;
	}

	private void drawGameIfThereIsNoBlankPositionLeft() {
		if (moves.size() == 9) {
			result = GameResult.DRAW;
			over = true;
		}
	}

	private boolean checkHorizontalRows() {
		for (int i = 0; i < grid.length; i = i + 3) {
			if (check(i, i + 1, i + 2)) {
				return over;
			}
		}
		return false;
	}
	
	private boolean checkVerticalRows() {
		for (int i = 0; i <= 2; i++) {
			if (check(i, i + 3, i + 6)) {
				return over;
			}
		}
		return false;
	}
	
	private boolean checkDiagonalRows() {
		return check(0, 4, 8) || check(2, 4, 6);
	}

	private boolean check(int first, int second, int third) {
		if (isEmpty(first) || isEmpty(second) || isEmpty(third)) {
			return false;
		}
		if (grid[first] == grid[second] && grid[second] == grid[third]) {
			over = true;
		}
		return over;
	}
	
	public void undo() {
		validateUndo();
		resetLastMove();
	}

	private void resetLastMove() {
		Move lastMove = moves.pop();
		grid[lastMove.positionIndex()] = BLANK;
	}

	private void validate(Position position, Mark mark) {
		if (this.isOver()) {
			throw new GameOverException();
		}
		if (isFilled(position.index())) {
			throw new FilledPositionException();
		}
		if (lastMove().sameMark(mark)) {
			throw new InvalidPlayerException();
		}
	}
	
	private void validateUndo() {
		if (this.isOver()) {
			throw new GameOverException();
		}
		if (moves.empty()) {
			throw new CannotUndoException();
		}
	}
	
	private boolean isFilled(int position) {
		return grid[position] != null;
	}
	
	private boolean isEmpty(int position) {
		return !isFilled(position);
	}

	public Move lastMove() {
		return moves.empty() ? Move.EMPTY : moves.peek();
	}

	public boolean isOver() {
		return over;
	}

	public GameResult getResult() {
		return result;
	}

}
