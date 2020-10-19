package com.game.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Kalah {
	
	private int gameId;
	private GameStatus status;
	private final int[] pits = new int[14];
	private int selectedPit;
	private int lastPitPosition;
	private Player currentPlayer;
	private Player nextPlayer;
	private String winner;

	
	
}
