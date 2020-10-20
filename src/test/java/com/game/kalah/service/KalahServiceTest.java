package com.game.kalah.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.game.kalah.cache.KalahCacheManager;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.GameStatus;
import com.game.kalah.model.Kalah;
import com.game.kalah.service.rules.KalahRule;
import com.game.kalah.utils.KalahHelper;


@RunWith(MockitoJUnitRunner.class)
public class KalahServiceTest {

	@InjectMocks
	KalahService kalahService = new KalahService();
	
	@Mock
	List<KalahRule> kalahRuleEngine;

	@Mock
	KalahCacheManager kalahCache;
	
	@Test
	public void startGameTest() {
		
		Kalah kalah = kalahService.startGame();

		Assert.assertArrayEquals(new int[] { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 }, kalah.getPits());

	}

	@Test
	public void playTurnTest() {

		int gameId = 1;
		int pitId = 0;
		
		when(kalahCache.get(any(Integer.class))).thenReturn(getKalah());
		Kalah kalah = kalahService.playTurn(gameId, pitId);	
		Assert.assertNotNull(kalah);

	}
	
	@Test(expected=KalahServiceException.class)
	public void playTurnGameNotPresent() {

		int gameId = 1;
		int pitId = 0;
		
		when(kalahCache.get(any(Integer.class))).thenReturn(null);
		kalahService.playTurn(gameId, pitId);	

	}
	
	@Test(expected=KalahServiceException.class)
	public void playTurnGameFinished() {

		int gameId = 1;
		int pitId = 0;
		Kalah kalah = getKalah();
		kalah.setStatus(GameStatus.FINISHED);
		
		when(kalahCache.get(any(Integer.class))).thenReturn(kalah);
		kalahService.playTurn(gameId, pitId);	

	}
	
	private Kalah getKalah() {
		
		KalahHelper kalahHelper = new KalahHelper();
		
		Kalah kalah = new Kalah();
		kalah.setStatus(GameStatus.INPROGRESS);
		kalahHelper.populateStonesInPits(kalah);
		
		return kalah;
		
	}
	
	

}
