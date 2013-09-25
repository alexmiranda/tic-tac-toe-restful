package com.bravisoftware.samples.tictactoe.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.Move;
import com.bravisoftware.samples.tictactoe.resource.GameResource;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;
import com.bravisoftware.samples.tictactoe.service.GameFacade;

@Controller
@RequestMapping(value = "/api/games", produces = { MediaType.APPLICATION_JSON_VALUE })
public class GameController {

	private final GameResourceAssembler assembler;
	private final GameFacade gameCenter;

	@Autowired
	public GameController(GameResourceAssembler assembler, GameFacade gameCenter) {
		this.assembler = assembler;
		this.gameCenter = gameCenter;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> createNewGame() {
		HttpHeaders headers = new HttpHeaders();
		Long gameId = gameCenter.createNewGame();
		headers.setLocation(linkTo(methodOn(getClass()).getGame(gameId)).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
	@ResponseBody
	public GameResource getGame(@PathVariable Long gameId) {
		Game game = gameCenter.loadGame(gameId);
		return assembler.toResource(game);
	}
	
	@RequestMapping(value = "/{gameId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> play(@PathVariable Long gameId, @RequestBody Move move) {
		gameCenter.play(gameId, move.position(), move.mark());
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{gameId}/lastMove", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> undoMove(@PathVariable Long gameId) {
		gameCenter.undoLastMove(gameId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
