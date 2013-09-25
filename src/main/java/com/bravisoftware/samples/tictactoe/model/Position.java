package com.bravisoftware.samples.tictactoe.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonValue;

public enum Position {
	TopLeftCorner(0), 
	TopEdge(1),
	TopRightCorner(2),
	LeftEdge(3),
	Center(4),
	RightEdge(5),
	BottonLeftCorner(6),
	BottonEdge(7),
	BottonRightCorner(8);
	
	@JsonIgnore
	private int index;
	
	@JsonCreator
	public static Position fromName(String name) {
		return Position.valueOf(name);
	}
	
	@JsonValue
	public String value() {
		return this.name();
	}
	
	Position(int index) {
		this.index = index;
	}
	
	public int index() {
		return index;
	}
}
