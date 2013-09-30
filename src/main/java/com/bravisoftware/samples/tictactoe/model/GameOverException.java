package com.bravisoftware.samples.tictactoe.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "game is over")
public class GameOverException extends BadMoveException {

	private static final long serialVersionUID = 2421000651201026216L;

}
