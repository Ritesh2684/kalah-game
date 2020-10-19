package com.game.kalah.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.game.kalah.model.Kalah;
/**
 * This class caches the Kalah object for next reference
 * @author Ritesh Agarwal
 *
 */
@Component
public class KalahCacheManager {
	
	private static KalahCacheManager kalahCache;
	
	private static Map<Integer,Kalah> kalahStore;
	
	public static KalahCacheManager getCache() {
		if(null == kalahCache) {
			kalahCache = new KalahCacheManager();
			kalahStore = new HashMap<>();
		}
		
		return kalahCache;
	}
	
	/**
	 * This method saves kalah Object to cache
	 * @param kalah {@link Kalah}
	 */
	public void save(Kalah kalah) {
		kalahStore.put(kalah.getGameId(),kalah);		
	}
	
	/**
	 * This method retrives kalah for the provided game id
	 * @param gameId id of the game to be retrieved
	 * @return kalah {@link Kalah}
	 */
	public Kalah get(int gameId) {
		return kalahStore.get(gameId);		
	}
	
	/**
	 * This method removes kalah for the provided game id
	 * @param gameId id of the game to be removed
	 * @return kalah {@link Kalah}
	 */
	public Kalah remove(int gameId) {
		return kalahStore.remove(gameId);	
	}

}
