package com.bravisoftware.samples.tictactoe.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.bravisoftware.samples.tictactoe.controller.GameController;
import com.bravisoftware.samples.tictactoe.model.Game;

public class GameResourceAssembler extends ResourceAssemblerSupport<Game, GameResource> {

	public GameResourceAssembler() {
		super(GameController.class, GameResource.class);
	}

	@Override
	public GameResource toResource(Game game) {
		GameResource resource = createResourceWithId(game.getId(), game);
		resource.setGame(game);
		
		return resource;
	}
	
}
