package com.bravisoftware.samples.tictactoe.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Move {
	
	public static final Move EMPTY = new EmptyMove();
	
	@JsonProperty("Position")
	private Position position;
	
	@JsonProperty("Mark")
	private Mark mark;

	@JsonCreator
	public Move(@JsonProperty("Position") Position position, @JsonProperty("Mark") Mark mark) {
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
	
	public Position position() {
		return this.position;
	}
	
	public Mark mark() {
		return this.mark;
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

	public static Position toPosition(int index) {
		for (Position position : Position.values()) {
			if(position.index() == index)
				return position;
		}
		throw new PositionNotFoundException();
	}
}