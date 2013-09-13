package com.bravisoftware.samples.tictactoe.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveTests {
	
	@Test
	public void should_be_the_same_position() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		assertTrue(move.samePosition(Position.TopLeftCorner));
	}
	
	@Test
	public void should_not_be_the_same_position() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		assertFalse(move.samePosition(Position.Center));
	}
	
	@Test
	public void should_be_the_same_mark() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		assertTrue(move.sameMark(Mark.X));
	}
	
	@Test
	public void should_not_be_the_same_mark() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		assertFalse(move.sameMark(Mark.O));
	}

}
