package com.game.kalah.service.rules;

import org.junit.Before;
import org.junit.Test;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahHelper;

import org.junit.Assert;

public class CheckLastStoneInHomePitTest {
	
	KalahHelper kalahHelper = new KalahHelper();
	Kalah kalah;
	DistributeStonesInPits distributeStonesInPits = new DistributeStonesInPits();
	
	@Before
	public void setUp() {		
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		kalah.setCurrentPlayer(Player.NORTH);
		
	}
	
	@Test
	public void checkNextPlayerWhenLastStoneInHomePit() {
		
		
		CheckLastStoneInHomePit checkLastStoneInHomePit = new CheckLastStoneInHomePit();
		kalah.setSelectedPit(0);
		
		distributeStonesInPits.applyRule(kalah);
		
		checkLastStoneInHomePit.applyRule(kalah);
		
		Assert.assertEquals(Player.NORTH, kalah.getNextPlayer());
		
	}
	
	@Test
	public void checkNextPlayerWhenLastStoneNotInHomePit() {
		
		CheckLastStoneInHomePit checkLastStoneInHomePit = new CheckLastStoneInHomePit();
		kalah.setSelectedPit(1);
		
		distributeStonesInPits.applyRule(kalah);
		
		checkLastStoneInHomePit.applyRule(kalah);
		
		Assert.assertEquals(Player.SOUTH, kalah.getNextPlayer());
		
	}	

}
