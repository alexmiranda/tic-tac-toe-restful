package com.bravisoftware.samples.tictactoe.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class MarkTests {

	@Test
	void switch_mark_should_return_different_mark() throws Exception {
		assertThat(Mark.X.switchMark(), equalTo(Mark.O));
		assertThat(Mark.O.switchMark(), equalTo(Mark.X));
	}
	
}
