package com.game.kalah.service.rules;

import java.util.Arrays;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;

import lombok.extern.slf4j.Slf4j;

/**
 * This class verifies if the last pit position is player's home pit then same
 * player gets the next turn
 * 
 * @author Ritesh Agarwal
 *
 */
@Slf4j
@Component
@Order(3)
public class CheckLastStoneInHomePit implements KalahRule {

	/**
	 * This method verifies if the last pit position is player's home pit then same
	 * player gets the next turn
	 * 
	 * @param kalah {@link Kalah}
	 */
	@Override
	public void applyRule(Kalah kalah) {

		Arrays.stream(Player.values()).forEach(player -> {
			if (player.equals(kalah.getCurrentPlayer()) && player.getHomePit() == kalah.getLastPitPosition()) {

				log.debug("Last stone has landed in Players home pit, hence same player {} take turn again ",
						player.name());

				kalah.setNextPlayer(player);
			}
		});

		if (null == kalah.getNextPlayer()) {
			if (Player.NORTH.equals(kalah.getCurrentPlayer())) {
				kalah.setNextPlayer(Player.SOUTH);
			} else {
				kalah.setNextPlayer(Player.NORTH);
			}
		}

	}

}
