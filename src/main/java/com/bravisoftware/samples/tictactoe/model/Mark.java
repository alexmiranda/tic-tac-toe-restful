package com.bravisoftware.samples.tictactoe.model;

public enum Mark {
	X {
		@Override
		Mark switchMark() {
			return Mark.O;
		}
		
		@Override
		public String toString() {
			return "X";
		}
		
	}, O {
		@Override
		Mark switchMark() {
			return Mark.X;
		}
		
		@Override
		public String toString() {
			return "O";
		}
	};
	
	abstract Mark switchMark();
	
}
