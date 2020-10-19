package com.game.kalah.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.kalah.cache.KalahCacheManager;
import com.game.kalah.exception.Errors;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.GameStatus;
import com.game.kalah.model.Kalah;
import com.game.kalah.service.rules.KalahRule;
import com.game.kalah.utils.KalahHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KalahService {

	KalahCacheManager kalahCache = KalahCacheManager.getCache();

	KalahHelper kalahServiceHelper = new KalahHelper();

	@Autowired
	List<KalahRule> kalahRuleEngine;

	/**
	 * This method initialize the kalah Object with assigning 6 stones to all the
	 * non home pits. - generates gameId - set game status INPROGRESS - stores kalah
	 * object in cache
	 * 
	 * @return kalah {@link Kalah}
	 */
	public Kalah startGame() {

		Kalah kalah = new Kalah();
		kalah.setGameId(new Random().nextInt(Integer.MAX_VALUE));
		kalah.setStatus(GameStatus.INPROGRESS);
		kalahServiceHelper.populateStonesInPits(kalah);
		kalahCache.save(kalah);
		return kalah;

	}
	
	/**
	 * This method 
	 * 1. distribute stones of the selected pit as per the Kalah Rules
	 * 2. identify the next user
	 * 3. verify the game status
	 * 4. When game is over, identifies the winner
	 * 
	 * @param gameIdInput id of the game
	 * @param pitIdInput  id of the selected pit
	 * @return kalah {@link Kalah}
	 * @throws kalahServiceException {@link KalahServiceException}
	 */
	public Kalah playTurn(String gameIdInput, String pitIdInput) throws KalahServiceException{
		
		int gameId = Integer.valueOf(gameIdInput);

		// subtract 1 to match with array position
		int pitId = Integer.valueOf(pitIdInput) - 1;

		Kalah kalah = kalahCache.get(Integer.valueOf(gameId));
		
		if(null != kalah) {
			
			kalah.setCurrentPlayer(null);

			if (GameStatus.INPROGRESS.equals(kalah.getStatus())) {

				kalahServiceHelper.identifyPlayer(kalah, pitId);
				kalah.setSelectedPit(pitId);

				log.debug("Current player is {} taking turn for pit ", kalah.getCurrentPlayer().name(),
						kalah.getSelectedPit());			
				
				kalahRuleEngine.forEach(kalahRule -> kalahRule.applyRule(kalah));
				
				log.debug("Saving Kalah object to Cache");
				kalahCache.save(kalah);

			} else {
				throw new KalahServiceException(Errors.INVALID_STATE.getErrorCode(),
						Errors.INVALID_STATE.getErrorMessage());
			}
		}else {
			throw new KalahServiceException(Errors.GAME_DOES_NOT_EXIST.getErrorCode(),
					Errors.GAME_DOES_NOT_EXIST.getErrorMessage());
		}		

		return kalah;

	}

}
