package com.bravisoftware.samples.tictactoe.controller;

import javax.swing.plaf.SliderUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.resource.GameResource;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RequestMapping(value = "api/games")
@Controller
public class GameController {

	private final GameResourceAssembler gameResourceAssembler;

	@Autowired
	public GameController(GameResourceAssembler gameResourceAssembler) {
		this.gameResourceAssembler = gameResourceAssembler;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> createNewGame() {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(methodOn(getClass()).getGame(1L)).slash(1).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	public GameResource getGame(Long id) {
		Game game = new Game();
		return gameResourceAssembler.toResource(game);
	}

}
