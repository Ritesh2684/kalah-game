package com.game.kalah.service.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahHelper;

public class CheckIsMoveValidTest {
	
	KalahHelper kalahHelper = new KalahHelper();
	Kalah kalah;
	
	@Before
	public void setUp() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);		
		kalah.setNextPlayer(Player.SOUTH);
		
	}
	
	@Test(expected = KalahServiceException.class)
	public void checkInvalidMove() {
		
		kalah.setCurrentPlayer(Player.NORTH);
		
		CheckIsMoveValid checkIsMoveValid = new CheckIsMoveValid();
		checkIsMoveValid.applyRule(kalah);		
		
	}
	
	@Test
	public void checkValidMove() {
		
		kalah.setCurrentPlayer(Player.SOUTH);
		
		CheckIsMoveValid checkIsMoveValid = new CheckIsMoveValid();
		checkIsMoveValid.applyRule(kalah);
		
		Assert.assertNull(kalah.getNextPlayer());
		
	}

}
