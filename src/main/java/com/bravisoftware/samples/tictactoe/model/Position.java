package com.bravisoftware.samples.tictactoe.model;

public enum Position {
	TopLeftCorner(0), 
	Center(4);
	
	private int index;
	
	Position(int index) {
		this.index = index;
	}
	
	public int index() {
		return index;
	}
}
