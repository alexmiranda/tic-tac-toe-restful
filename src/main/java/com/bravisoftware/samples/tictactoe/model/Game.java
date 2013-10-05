package com.bravisoftware.samples.tictactoe.model;

import java.util.Stack;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Game {
	
	private static final Mark BLANK = null;

	@JsonIgnore
	private final Mark [] grid = new Mark[9];
	
	@JsonIgnore
	private Long id;
	
	@JsonIgnore
	private Stack<Move> moves = new Stack<Move>();
	
	@JsonProperty(value = "status")
	private GameStatus status;
	
	@JsonIgnore
	private GameResult result = GameResult.OPEN;

	public Game() { }
	
	public Game(long id) {
		this();
		this.setId(id);
	}
	
	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}
	
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
			status = GameStatus.OVER;
		}
	}

	private boolean checkHorizontalRows() {
		for (int i = 0; i < grid.length; i = i + 3) {
			if (check(i, i + 1, i + 2)) {
				return status == GameStatus.OVER;
			}
		}
		return false;
	}
	
	private boolean checkVerticalRows() {
		for (int i = 0; i <= 2; i++) {
			if (check(i, i + 3, i + 6)) {
				return status == GameStatus.OVER;
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
			this.status = GameStatus.OVER;
		}
		return status == GameStatus.OVER;
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
		return status == GameStatus.OVER;
	}

	public GameResult getResult() {
		return result;
	}
	
	public GameStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(GameStatus status) {
		if (isOver() && status != GameStatus.OVER) {
			throw new GameOverException();
		}
		
		this.status = status;
		if (status == GameStatus.OVER) {
			this.result = GameResult.DRAW;
		}	
	}
}
