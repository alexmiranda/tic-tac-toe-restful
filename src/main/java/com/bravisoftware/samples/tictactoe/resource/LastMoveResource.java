package com.bravisoftware.samples.tictactoe.resource;

import org.springframework.hateoas.ResourceSupport;

import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Move;

public class LastMoveResource extends ResourceSupport{
	
	private Move move;
	
	public LastMoveResource(Move move){
		this.move = move;
	}
	
	public String getMark() {
		return this.move.sameMark(Mark.X) ? "X" : "O";
	}

	public String getPosition() {
		return String.valueOf(this.move.positionIndex());
	}

}
