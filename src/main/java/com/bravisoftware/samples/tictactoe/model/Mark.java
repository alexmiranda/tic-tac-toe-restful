package com.bravisoftware.samples.tictactoe.model;

public enum Mark {
	X {
		@Override
		Mark switchMark() {
			return Mark.O;
		}
	}, O {
		@Override
		Mark switchMark() {
			return Mark.X;
		}
	};
	abstract Mark switchMark();
}
