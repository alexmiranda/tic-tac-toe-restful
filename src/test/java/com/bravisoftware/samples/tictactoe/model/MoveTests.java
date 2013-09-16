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
	
	@Test
	public void should_return_true_if_the_position_and_mark_are_equal() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		assertTrue(move.is(Position.TopLeftCorner, Mark.X));
	}
	
	@Test
	public void should_return_false_if_either_position_or_mark_is_different() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		assertFalse(move.is(Position.TopLeftCorner, Mark.O));
		assertFalse(move.is(Position.TopRightCorner, Mark.X));
	}

}
