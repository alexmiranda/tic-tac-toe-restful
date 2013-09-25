package com.bravisoftware.samples.tictactoe.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bravisoftware.samples.tictactoe.controller.GameController;
import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.Move;

@Component
public class GameResourceAssembler extends ResourceAssemblerSupport<Game, GameResource> {

	public GameResourceAssembler() {
		super(GameController.class, GameResource.class);
	}

	@Override
	public GameResource toResource(Game game) {
		GameResource resource = createResourceWithId(game.getId(), game);
		resource.setGame(game);
		
		Move lastMove = game.lastMove();
		if (lastMove != Move.EMPTY) {
			Link moveLink = linkTo(methodOn(GameController.class).undoMove(game.getId())).withRel("undo");
			resource.getLastMove().add(moveLink);
		}
		
		return resource;
	}
	
}
