package com.bravisoftware.samples.tictactoe.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
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
		Long moveId = new Long(game.lastMove().positionIndex());
		Link moveLink = linkTo(methodOn(GameController.class).undoMove(game.getId(), moveId)).withRel("undo");
		System.out.println(moveLink.getHref());
		resource.getLastMove().add(moveLink);
		return resource;
	}
	
}
