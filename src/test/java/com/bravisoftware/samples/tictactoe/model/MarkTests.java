package com.bravisoftware.samples.tictactoe.model;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

public class MarkTests {

	@Test
	public void switch_mark_should_return_different_mark() throws Exception {
		assertThat(Mark.X.switchMark(), equalTo(Mark.O));
		assertThat(Mark.O.switchMark(), equalTo(Mark.X));
	}
	
}
