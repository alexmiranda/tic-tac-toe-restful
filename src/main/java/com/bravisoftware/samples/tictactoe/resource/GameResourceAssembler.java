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
		final Long gameId = game.getId();
		
		GameResource resource = createResourceWithId(gameId, game);
		resource.setGame(game);
		
		if (!game.isOver()) {
			Link playLink = linkTo(methodOn(GameController.class).play(gameId, null)).withRel("play");
			resource.add(playLink);
		}
		
		Move lastMove = game.lastMove();
		if (lastMove != Move.EMPTY && !game.isOver()) {
			Link moveLink = linkTo(methodOn(GameController.class).undoMove(gameId)).withRel("undo");
			resource.add(moveLink);
		}
		
		return resource;
	}
	
}
