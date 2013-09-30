package com.bravisoftware.samples.tictactoe.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static com.bravisoftware.samples.tictactoe.util.TestUtil.convertObjectToJsonBytes;
import static com.bravisoftware.samples.tictactoe.util.GameUtils.completeGameWithDraw;
import static com.bravisoftware.samples.tictactoe.util.GameUtils.completeGameWithWinner;

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
	public void getting_a_brand_new_game_returns_ok() throws Exception {
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void new_game_returns_correct_status_in_response_body() throws Exception {
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(jsonPath("$.status", is("Open")));
	}
	
	@Test
	public void new_game_constains_link_to_itself() throws Exception {
		String selfLink = String.format("/api/games/%s", game.getId().toString());
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(jsonPath("$.links..[?(@.rel == 'self')][0].href", endsWith(selfLink)));
	}
	
	@Test
	public void new_game_constains_link_to_play() throws Exception {
		String playLink = String.format("/api/games/%s", game.getId().toString());
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(jsonPath("$.links..[?(@.rel == 'play')][0].href", endsWith(playLink)));
	}
	
	@Test
	public void new_game_does_not_constain_link_to_undo() throws Exception {
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(jsonPath("$.links..[?(@.rel == 'undo')]").doesNotExist());
	}
	
	@Test
	public void game_in_progress_has_last_move_specified_in_body() throws Exception {
		game.play(Position.BottonEdge, Mark.X);	
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(jsonPath("$.lastMove.position", is("BottonEdge")))
				.andExpect(jsonPath("$.lastMove.mark", is("X")));
	}
	
	@Test
	public void game_in_progress_has_self_play_and_undo_links() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
				.andExpect(jsonPath("$.links..rel", containsInAnyOrder("self", "play", "undo")));
	}
	
	@Test
	public void get_a_non_existing_game_returns_404_not_found() throws Exception {		
		mockMvc.perform(get("/api/games/{0}", NON_EXISTING_GAME_ID))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is("404")))
				.andExpect(jsonPath("$.reason").exists());
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
	public void cannot_play_without_passing_the_position_and_returns_400_bad_request() throws Exception {		
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
	public void should_return_409_conflict_status_when_filled_position_is_played() throws Exception {
		game.play(Position.TopLeftCorner, Mark.X);
		Move move = new Move(Position.TopLeftCorner, Mark.O);
		mockMvc.perform(post("/api/games/{0}", game.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(move)))
                .andExpect(status().isConflict());
	}
	
	@Test
	public void deleting_the_last_move_returns_200_if_the_game_is_in_progress() throws Exception {
		game.play(Position.BottonEdge, Mark.X);
		mockMvc.perform(delete("/api/games/{0}/lastMove", EXISTING_GAME_ID))
				.andExpect(status().isOk());
	}
	
	@Test
	public void cannot_undo_last_move_if_there_is_no_moves_and_returns_409_conflict() throws Exception {
		mockMvc.perform(delete("/api/games/{0}/lastMove", EXISTING_GAME_ID))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void when_game_is_over_with_a_draw_status_should_be_draw() throws Exception {
		completeGameWithDraw(game);
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
			.andExpect(jsonPath("$.status", is("Draw")));
	}
	
	@Test
	public void when_game_is_over_with_X_winning_status_should_be_x_wins() throws Exception {
		completeGameWithWinner(game, Mark.X);
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
			.andExpect(jsonPath("$.status", is("X wins")));
	}
	
	@Test
	public void when_game_is_over_with_nought_winning_status_should_be_x_wins() throws Exception {
		completeGameWithWinner(game, Mark.O);
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
			.andExpect(jsonPath("$.status", is("O wins")));
	}
	
	@Test
	public void when_game_is_over_it_should_not_contain_link_to_play() throws Exception {
		completeGameWithDraw(game);
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
			.andExpect(jsonPath("$.links..[?(@.rel == 'play')]").doesNotExist());
	}
	
	@Test
	public void when_game_is_over_it_should_not_contain_link_to_undo() throws Exception {
		completeGameWithDraw(game);
		mockMvc.perform(get("/api/games/{0}", EXISTING_GAME_ID))
			.andExpect(jsonPath("$.links..[?(@.rel == 'undo')]").doesNotExist());
	}
}