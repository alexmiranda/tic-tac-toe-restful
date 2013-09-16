package com.bravisoftware.samples.tictactoe.model;

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
	
	private int index;
	
	Position(int index) {
		this.index = index;
	}
	
	public int index() {
		return index;
	}
}
