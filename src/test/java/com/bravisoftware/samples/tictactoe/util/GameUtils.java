package com.bravisoftware.samples.tictactoe.util;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Position;

public class GameUtils {
	
	public static void completeGameWithDraw(Game game) {
		playInSequence(game, Position.TopLeftCorner, Position.Center,
				   Position.TopRightCorner, Position.TopEdge, 
				   Position.BottonEdge, Position.LeftEdge, 
				   Position.RightEdge, Position.BottonRightCorner,
				   Position.BottonLeftCorner);		
	}
	
	public static void completeGameWithWinner(Game game, Mark expectedWinner) {
		playInSequence(game, expectedWinner, 
				   Position.TopLeftCorner, Position.Center,
				   Position.TopRightCorner, Position.TopEdge, 
				   Position.BottonEdge, Position.LeftEdge, 
				   Position.RightEdge, Position.BottonLeftCorner, 
				   Position.BottonRightCorner);
	}
	
	public static void playInSequence(Game game, Position... positions) {
		playInSequence(game, Mark.X, positions);
	}
	
	public static void playInSequence(Game game, Mark mark, Position... positions) {
		for (Position position : positions) {
			game.play(position, mark);
			mark = mark.switchMark();
		}
	}
	
}
