package com.bravisoftware.samples.tictactoe.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTests {
	
	private Game game;
	
	@Before
	public void setup() {
		game = new Game();
	}

	@Test
	public void when_move_played_it_should_be_the_last_in_the_game() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		Move lastMove = game.lastMove();
		assertTrue(lastMove.is(Position.TopLeftCorner, Mark.X));
	}
	
	@Test(expected = BadMoveException.class)
	public void cannot_play_the_same_mark_again() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.Center, Mark.X);
	}
	
	@Test(expected = BadMoveException.class)
	public void cannot_play_in_the_same_last_position() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.TopLeftCorner, Mark.O);
	}
	
	@Test(expected = BadMoveException.class)
	public void cannot_play_in_a_filled_position() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.Center, Mark.O);
		game.play(Position.TopLeftCorner, Mark.X);
	}
	
	@Test
	public void playing_three_in_the_first_horizontal_row_wins_the_game() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.BottonLeftCorner, Mark.O);
		game.play(Position.TopEdge, Mark.X);
		game.play(Position.BottonEdge, Mark.O);
		game.play(Position.TopRightCorner, Mark.X);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_second_horizontal_row_wins_the_game() throws Exception {
		game.play(Position.LeftEdge, Mark.X);
		game.play(Position.BottonLeftCorner, Mark.O);
		game.play(Position.Center, Mark.X);
		game.play(Position.BottonEdge, Mark.O);
		game.play(Position.RightEdge, Mark.X);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_third_horizontal_row_wins_the_game() throws Exception {
		game.play(Position.BottonLeftCorner, Mark.X);
		game.play(Position.TopLeftCorner, Mark.O);
		game.play(Position.BottonEdge, Mark.X);
		game.play(Position.TopEdge, Mark.O);
		game.play(Position.BottonRightCorner, Mark.X);
		assertTrue(game.isOver());
	}
}
