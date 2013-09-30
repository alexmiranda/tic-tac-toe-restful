package com.bravisoftware.samples.tictactoe.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "invalid player sequence")
public class InvalidPlayerException extends BadMoveException {

	private static final long serialVersionUID = -688158404287641108L;

}
