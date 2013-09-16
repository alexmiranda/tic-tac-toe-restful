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
	
	@Test(expected = InvalidPlayerException.class)
	public void cannot_play_the_same_mark_again() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.Center, Mark.X);
	}
	
	@Test(expected = FilledPositionException.class)
	public void cannot_play_in_the_same_last_position() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.TopLeftCorner, Mark.O);
	}
	
	@Test(expected = FilledPositionException.class)
	public void cannot_play_in_a_filled_position() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		game.play(Position.Center, Mark.O);
		game.play(Position.TopLeftCorner, Mark.X);
	}
	
	@Test
	public void playing_three_in_the_first_horizontal_row_wins_the_game() throws Exception {
		playInSequence(Position.TopLeftCorner, Position.BottonLeftCorner, 
					   Position.TopEdge, Position.BottonEdge, 
					   Position.TopRightCorner);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_second_horizontal_row_wins_the_game() throws Exception {
		playInSequence(Position.LeftEdge, Position.BottonLeftCorner, 
				       Position.Center, Position.BottonEdge, 
				       Position.RightEdge);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_third_horizontal_row_wins_the_game() throws Exception {
		playInSequence(Position.BottonLeftCorner, Position.TopLeftCorner, 
					   Position.BottonEdge, Position.TopEdge, 
					   Position.BottonRightCorner);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_first_vertical_row_wins_the_game() throws Exception {
		playInSequence(Position.TopLeftCorner, Position.TopRightCorner, 
					   Position.LeftEdge, Position.RightEdge, 
					   Position.BottonLeftCorner);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_second_vertical_row_wins_the_game() throws Exception {
		playInSequence(Position.TopEdge, Position.TopRightCorner, 
				       Position.Center, Position.RightEdge, 
				       Position.BottonEdge);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_third_vertical_row_wins_the_game() throws Exception {
		playInSequence(Position.TopRightCorner, Position.TopLeftCorner, 
				       Position.RightEdge, Position.LeftEdge,  
				       Position.BottonRightCorner);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_first_diagonal_row_wins_the_game() throws Exception {
		playInSequence(Position.TopLeftCorner, Position.LeftEdge, 
					   Position.Center, Position.RightEdge,
					   Position.BottonRightCorner);
		assertTrue(game.isOver());
	}
	
	@Test
	public void playing_three_in_the_second_diagonal_row_wins_the_game() throws Exception {
		playInSequence(Position.TopRightCorner, Position.LeftEdge, 
				       Position.Center, Position.RightEdge, 
				       Position.BottonLeftCorner);
		assertTrue(game.isOver());
	}
	
	@Test(expected = GameOverException.class)
	public void cannot_play_when_game_is_over() throws Exception {
		playInSequence(Position.TopRightCorner, Position.LeftEdge, 
			       Position.Center, Position.RightEdge, 
			       Position.BottonLeftCorner);
		game.play(Position.TopLeftCorner, Mark.O);
	}
	
	private void playInSequence(Position... positions) {
		Mark mark = Mark.X;
		for (Position position : positions) {
			game.play(position, mark);
			mark = mark.switchMark();
		}
	}
}
