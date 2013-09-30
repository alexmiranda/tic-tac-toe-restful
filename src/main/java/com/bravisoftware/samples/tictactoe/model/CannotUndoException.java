package com.bravisoftware.samples.tictactoe.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "there is no moves")
public class CannotUndoException extends BadMoveException {

	private static final long serialVersionUID = 6858640398708199095L;

}
