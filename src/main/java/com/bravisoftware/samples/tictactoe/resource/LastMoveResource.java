package com.bravisoftware.samples.tictactoe.resource;

import org.springframework.hateoas.ResourceSupport;

import com.bravisoftware.samples.tictactoe.model.Move;

public class LastMoveResource extends ResourceSupport{
	
	private Move move;
	
	public LastMoveResource(Move move){
		this.move = move;
	}
	
	public String getMark() {
		return this.move.mark().name();
	}

	public String getPosition() {
		return this.move.position().name();
	}

}
