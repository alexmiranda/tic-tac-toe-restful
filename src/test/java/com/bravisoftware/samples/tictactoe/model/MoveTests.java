package com.bravisoftware.samples.tictactoe.model;

import static org.junit.Assert.*;

import org.hibernate.param.PositionalParameterSpecification;
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
	
	@Test
	public void should_return_correct_position(){
		Position topLeftCorner = Move.toPosition(Position.TopLeftCorner.index());
		Position topEdge = Move.toPosition(Position.TopEdge.index());
		Position topRightCorner = Move.toPosition(Position.TopRightCorner.index());
		Position leftEdge = Move.toPosition(Position.LeftEdge.index());
		Position center = Move.toPosition(Position.Center.index());
		Position rightEdge = Move.toPosition(Position.RightEdge.index());
		Position bottonLeftCorner = Move.toPosition(Position.BottonLeftCorner.index());
		Position bottonEdge = Move.toPosition(Position.BottonEdge.index());
		Position bottonRightCorner = Move.toPosition(Position.BottonRightCorner.index());
		
		
		assertTrue(Position.TopLeftCorner.equals(topLeftCorner));
		assertTrue(Position.TopEdge.equals(topEdge));
		assertTrue(Position.TopRightCorner.equals(topRightCorner));
		assertTrue(Position.LeftEdge.equals(leftEdge));
		assertTrue(Position.Center.equals(center));
		assertTrue(Position.RightEdge.equals(rightEdge));
		assertTrue(Position.BottonLeftCorner.equals(bottonLeftCorner));
		assertTrue(Position.BottonEdge.equals(bottonEdge));
		assertTrue(Position.BottonRightCorner.equals(bottonRightCorner));
	}
	
	@Test(expected = PositionNotFoundException.class)
	public void should_throw_exception_when_position_not_found(){
		Move.toPosition(-100);
	}

}
