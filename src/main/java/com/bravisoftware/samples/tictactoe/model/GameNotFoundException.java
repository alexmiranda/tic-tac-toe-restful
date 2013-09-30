package com.bravisoftware.samples.tictactoe.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "game does not exist")
public class GameNotFoundException extends GameException {

	private static final long serialVersionUID = 2971639200908659396L;

}
