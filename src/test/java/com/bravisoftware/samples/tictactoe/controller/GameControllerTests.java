package com.bravisoftware.samples.tictactoe.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static com.bravisoftware.samples.tictactoe.util.TestUtil.convertObjectToJsonBytes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bravisoftware.samples.tictactoe.config.TestContext;
import com.bravisoftware.samples.tictactoe.config.WebAppContext;
import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Move;
import com.bravisoftware.samples.tictactoe.model.Position;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;
import com.bravisoftware.samples.tictactoe.service.GameFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
public class GameControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private GameFacade gameCenter;

	@Autowired
	private GameResourceAssembler assembler;
	
	private Game game;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		game = new Game(1L);
		when(gameCenter.loadGame(eq(1L))).thenReturn(game);
	}

	@Test
	public void create_new_game() throws Exception {
		mockMvc.perform(post("/api/games"))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", endsWith("/api/games/1")));
	}

	@Test
	public void get_an_existing_game() throws Exception {		
		game.play(Position.BottonEdge, Mark.X);
				
		String movePathMatcher = String.format("/api/games/%s/lastMove", game.getId().toString());

		mockMvc.perform(get("/api/games/{0}", 1L))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.status", is("Open")))
				.andExpect(jsonPath("$.lastMove.position", is("BottonEdge")))
				.andExpect(jsonPath("$.lastMove.mark", is("X")))
				.andExpect(jsonPath("$.lastMove.links[0].rel", is("undo")))
				.andExpect(jsonPath("$.lastMove.links[0].href", endsWith(movePathMatcher)));
	}
	
	@Test
	public void should_play_and_return_status_accepted() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isAccepted());
	}
	
	@Test
	public void should_return_400_status_when_invalid_player() throws Exception {		
		game.play(Position.TopLeftCorner, Mark.X);
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_return_400_status_when_filled_position_played_again() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		Move move = new Move(Position.TopLeftCorner, Mark.O);
		
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_back_to_previous_move_when_undo_last_move() throws Exception {
		game.play(Position.BottonEdge, Mark.X);
		game.play(Position.BottonLeftCorner, Mark.O);
		
		mockMvc.perform(delete("/api/games/{0}/lastMove", 1L))
				.andExpect(status().isAccepted());
	}
	
}