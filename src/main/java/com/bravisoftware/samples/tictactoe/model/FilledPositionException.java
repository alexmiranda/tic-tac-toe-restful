package com.bravisoftware.samples.tictactoe.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "position is filled")
public class FilledPositionException extends BadMoveException {

	private static final long serialVersionUID = 6203836935842393252L;

}
