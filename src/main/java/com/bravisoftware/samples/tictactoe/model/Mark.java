package com.bravisoftware.samples.tictactoe.model;

public enum Mark {
	X {
		@Override
		public Mark switchMark() {
			return Mark.O;
		}
		
		@Override
		public String toString() {
			return "X";
		}
		
	}, O {
		@Override
		public Mark switchMark() {
			return Mark.X;
		}
		
		@Override
		public String toString() {
			return "O";
		}
	};
	
	public abstract Mark switchMark();
	
}
