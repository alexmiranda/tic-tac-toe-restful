package com.bravisoftware.samples.tictactoe.model;

public class Move {
	
	public static final Move EMPTY = new EmptyMove();
	
	private Position position;
	private Mark mark;

	public Move(Position position, Mark mark) {
		this.position = position;
		this.mark = mark;
	}

	public boolean is(Position position, Mark mark) {
		return samePosition(position) && sameMark(mark);
	}
	
	public boolean samePosition(Position position) {
		return this.position == position;
	}
	
	public boolean sameMark(Mark mark) {
		return this.mark == mark;
	}
	
	public int positionIndex() {
		return position.index();
	}
	
	private static class EmptyMove extends Move {

		public EmptyMove() {
			super(null, null);
		}
		
		@Override
		public boolean is(Position position, Mark x) {
			return false;
		}
		
	}
}