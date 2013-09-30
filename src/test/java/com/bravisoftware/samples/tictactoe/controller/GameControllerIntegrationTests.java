package com.bravisoftware.samples.tictactoe.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static com.bravisoftware.samples.tictactoe.util.TestUtil.convertObjectToJsonBytes;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
import com.bravisoftware.samples.tictactoe.model.GameRepository;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Move;
import com.bravisoftware.samples.tictactoe.model.Position;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;
import com.bravisoftware.samples.tictactoe.service.GameFacade;
import com.bravisoftware.samples.tictactoe.util.IntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@Category(IntegrationTest.class)
public class GameControllerIntegrationTests {

	private static final long EXISTING_GAME_ID = 1L;
	private static final long NON_EXISTING_GAME_ID = 2L;
	private static final String EXISTING_GAME_URI = "/api/games/1";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GameFacade gameCenter;

	@Autowired
	private GameResourceAssembler assembler;
	
	private Game game;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		forceGameReset();
	}

	private void forceGameReset() {
		game = new Game(EXISTING_GAME_ID);
		gameRepository.register(game);
	}

	@Test
	public void create_new_game() throws Exception {
		mockMvc.perform(post("/api/games"))
				.andExpect(status().isCreated())
				.andExpect(header().string("location", endsWith(EXISTING_GAME_URI)));
	}

	@Test
	public void get_an_existing_game() throws Exception {		
		game.play(Position.BottonEdge, Mark.X);
				
		String lastMoveLink = String.format("/api/games/%s/lastMove", game.getId().toString());
		String playLink = String.format("/api/games/%s", game.getId().toString());

		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("Open")))
				.andExpect(jsonPath("$.links..[?(@.rel == 'play')][0].href", endsWith(playLink)))
				.andExpect(jsonPath("$.links..[?(@.rel == 'undo')][0].href", endsWith(lastMoveLink)))
				.andExpect(jsonPath("$.lastMove.position", is("BottonEdge")))
				.andExpect(jsonPath("$.lastMove.mark", is("X")));
	}
	
	@Test
	public void get_a_non_existing_game_returns_404_not_found() throws Exception {		
		mockMvc.perform(get("/api/games/{0}", NON_EXISTING_GAME_ID))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void should_play_and_return_status_ok() throws Exception {
		Move move = new Move(Position.TopLeftCorner, Mark.X);
		
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isOk());
	}
	
	@Test
	public void cannot_play_with_empty_json_string_and_returns_400_bad_request() throws Exception {		
		final String content = "{}";
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void cannot_play_without_passing_the_position_and_returns_400_badrequest() throws Exception {		
		final String content = "{ mark: 'X' }";
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void cannot_play_without_passing_the_mark_and_returns_400_bad_request() throws Exception {		
		final String content = "{ position: 'TopLeftCorner' }";
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void should_return_409_conflict_status_when_invalid_player() throws Exception {		
		game.play(Position.TopLeftCorner, Mark.X);
		Move move = new Move(Position.Center, Mark.X);
		
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isConflict());
	}
	
	@Test
	public void should_return_409_conflict_status_when_filled_position_played_again() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		Move move = new Move(Position.TopLeftCorner, Mark.O);
		
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isConflict());
	}
	
	@Test
	public void deleting_the_last_move_returns_200_if_the_games_has_at_least_one_move() throws Exception {
		game.play(Position.BottonEdge, Mark.X);
		
		mockMvc.perform(delete("/api/games/{0}/lastMove", EXISTING_GAME_ID))
				.andExpect(status().isOk());
	}
	
	@Test
	public void cannot_undo_last_move_if_there_is_no_moves_and_returns_409_conflict() throws Exception {
		mockMvc.perform(delete("/api/games/{0}/lastMove", EXISTING_GAME_ID))
				.andExpect(status().isConflict());
	}
	
}