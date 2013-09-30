package com.bravisoftware.samples.tictactoe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bravisoftware.samples.tictactoe.model.GameException;

@ControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(GameException.class)
	@ResponseBody
	public Map<String, String> handleBadMoveException(GameException e, HttpServletResponse response) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		
		ResponseStatus annotation = e.getClass().getAnnotation(ResponseStatus.class);
		if (annotation != null) {
			response.setStatus(annotation.value().value());
			result.put("status", annotation.value().toString());
			result.put("reason", annotation.reason());
			return result;
		}
		
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return null;
	}
	
}
