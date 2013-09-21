package com.bravisoftware.samples.tictactoe.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bravisoftware.samples.tictactoe.controller.GameController;
import com.bravisoftware.samples.tictactoe.model.Game;

public class GameResourceAssembler extends ResourceAssemblerSupport<Game, GameResource> {

	public GameResourceAssembler() {
		super(GameController.class, GameResource.class);
	}

	@Override
	public GameResource toResource(Game game) {

		GameResource resource = instantiateResource(game);
		resource.setGame(game);
		resource.add(linkTo(methodOn(GameController.class).createNewGame()).withSelfRel());
		
		return resource;
	}
	
}
