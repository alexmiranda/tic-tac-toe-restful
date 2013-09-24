package com.bravisoftware.samples.tictactoe.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bravisoftware.samples.tictactoe.config.TestContext;
import com.bravisoftware.samples.tictactoe.config.WebAppContext;
import com.bravisoftware.samples.tictactoe.controller.GameController.MoveDTO;
import com.bravisoftware.samples.tictactoe.model.Game;
import com.bravisoftware.samples.tictactoe.model.GameFactory;
import com.bravisoftware.samples.tictactoe.model.GameRepository;
import com.bravisoftware.samples.tictactoe.model.Mark;
import com.bravisoftware.samples.tictactoe.model.Position;
import com.bravisoftware.samples.tictactoe.resource.GameResourceAssembler;
import com.bravisoftware.samples.tictactoe.service.GameFacade;
import com.bravisoftware.samples.tictactoe.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
public class GameControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private GameFacade gameFacade;

	@Autowired
	private GameResourceAssembler assembler;
	
	private Game game;

	@Before
	public void setUp() {
		
		Mockito.reset(gameFacade);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		game = new Game(1L);
		when(gameFacade.createNewGame()).thenReturn(game.getId());
		when(gameFacade.loadGame(game.getId())).thenReturn(game);
	}

	@Test
	@Ignore
	public void create_new_game() throws Exception {

		mockMvc.perform(post("/api/games"))
				.andExpect(status().isCreated())
				.andExpect(
						header().string("location",
								Matchers.containsString("/api/games/1")));

		verify(gameFacade, times(1)).createNewGame();
		verifyNoMoreInteractions(gameFacade);

	}

	@Test
	@Ignore
	public void get_an_existing_game() throws Exception {
		
		game.play(Position.BottonEdge, Mark.X);

		when(gameFacade.loadGame(1L)).thenReturn(game);

		mockMvc.perform(get("/api/games/{0}", 1L))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.status", is("Open")))
				.andExpect(jsonPath("$.lastMove.mark", is("X")))
				.andExpect(jsonPath("$.lastMove.position", is("7")));

		verify(gameFacade, times(1)).loadGame(game.getId());
		verifyNoMoreInteractions(gameFacade);
	}
	
	@Test
	@Ignore
	public void should_play_and_return_status_accepted() throws IOException, Exception{
		
		MoveDTO dto = new MoveDTO(1, "X");
		
		mockMvc.perform(post("/api/games/{0}/moves", game.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.status", is("Open")))
				.andExpect(jsonPath("$.lastMove.mark", is("X")))
				.andExpect(jsonPath("$.lastMove.position", is("1")));
	}
	
	@Test
	@Ignore
	public void should_return_400_status_when_invalid_player() throws IOException, Exception{
		
		game.play(Position.TopLeftCorner, Mark.X);
		
		MoveDTO dto = new MoveDTO(5, "X");
		
		mockMvc.perform(post("/api/games/{0}/moves", game.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	@Ignore
	public void should_return_400_status_when_filled_position_played_again() throws IOException, Exception{
		game.play(Position.TopLeftCorner, Mark.O);
		
		MoveDTO dto = new MoveDTO(0, "X");
		
		mockMvc.perform(post("/api/games/{0}/moves", game.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
	}
	
}