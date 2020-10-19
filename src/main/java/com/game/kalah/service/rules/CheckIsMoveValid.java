package com.game.kalah.service.rules;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.game.kalah.exception.Errors;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;

import lombok.extern.slf4j.Slf4j;

/**
 * This class checks if the correct player has taken the turn
 * 
 * @author Ritesh Agarwal
 *
 */
@Slf4j
@Component
@Order(1)
public class CheckIsMoveValid implements KalahRule {

	/**
	 * This class validates if the correct player has taken turn i.e. in the last
	 * move, if last stone landed in NORTH player's home pit, then this turn should
	 * be played by NORTH player itself
	 * 
	 * 
	 * @param kalah {@link Kalah}
	 */
	@Override
	public void applyRule(Kalah kalah) {

		if (null != kalah.getNextPlayer() && !kalah.getCurrentPlayer().equals(kalah.getNextPlayer())) {

			log.error("Expected Next Player is {} while turn is taken by {}", kalah.getNextPlayer(),
					kalah.getCurrentPlayer());

			throw new KalahServiceException(Errors.INVALID_TURN.getErrorCode(), Errors.INVALID_TURN.getErrorMessage());
		}

		kalah.setNextPlayer(null);
	}

}
