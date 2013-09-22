package com.bravisoftware.samples.tictactoe.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.resource.GameResource;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;
import com.bravisoftware.samples.tictactoe.service.GameService;

@RequestMapping(value = "api/games")
@Controller
public class GameController {

	private final GameResourceAssembler gameResourceAssembler;
	private final GameService service;

	@Autowired
	public GameController(GameResourceAssembler gameResourceAssembler, GameService service) {
		this.gameResourceAssembler = gameResourceAssembler;
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> createNewGame() {
		HttpHeaders headers = new HttpHeaders();
		Game game = service.createGame();
		headers.setLocation(linkTo(methodOn(getClass()).getGame(game.getId())).slash(game.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public GameResource getGame(@PathVariable Long id) {
		Game game = service.getGame(id);
		return gameResourceAssembler.toResource(game);
	}

}
