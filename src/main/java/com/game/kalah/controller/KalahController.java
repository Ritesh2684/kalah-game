/**
 * 
 */
package com.game.kalah.controller;

import static com.game.kalah.utils.KalahConstants.MESSAGE_200;
import static com.game.kalah.utils.KalahConstants.MESSAGE_201;
import static com.game.kalah.utils.KalahConstants.MESSAGE_400;
import static com.game.kalah.utils.KalahConstants.MESSAGE_404;
import static com.game.kalah.utils.KalahConstants.MESSAGE_500;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;
import com.game.kalah.service.KalahService;
import com.game.kalah.utils.KalahConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ritesh
 *
 */
@Slf4j
@RestController
@CrossOrigin
@Validated
public class KalahController {

	@Autowired
	KalahService kalahService;

	@PostMapping(value = "/games")
	@ApiOperation(value = "Start Kalah Game", notes = "Starts Kalah game and initiates the object to maintain the state of the game", response = Response.class)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value = { @ApiResponse(code = 201, message = MESSAGE_201),
			@ApiResponse(code = 400, message = MESSAGE_400), @ApiResponse(code = 404, message = MESSAGE_404),
			@ApiResponse(code = 500, message = MESSAGE_500) })
	public ResponseEntity<Object> startGame() throws KalahServiceException {

		log.debug("Start method startKalah");

		Kalah kalah = kalahService.startGame();

		System.out.println("Kalah is " + kalah);

		Link uri = linkTo(methodOn(KalahController.class).startGame()).slash(kalah.getGameId()).withSelfRel();

		log.info("Game successfully initiated");

		log.debug("End method startKalah");

		Map<String, Object> response = new HashMap<>();
		response.put(KalahConstants.ID, kalah.getGameId());
		response.put(KalahConstants.URI, uri.toUri());

		return new ResponseEntity<Object>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "/games/{gameId}/pits/{pitId}")
	@ApiOperation(value = "Make a move for Kalah", notes = "Play your turn with the provided game and selected pit id", response = Response.class)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value = { @ApiResponse(code = 200, message = MESSAGE_200),
			@ApiResponse(code = 400, message = MESSAGE_400), @ApiResponse(code = 404, message = MESSAGE_404),
			@ApiResponse(code = 500, message = MESSAGE_500) })
	public ResponseEntity<Object> playTurn(@PathVariable String gameId, @PathVariable String pitId)
			throws KalahServiceException {

		Map<String, Object> response = new HashMap<>();
		Map<Integer, Integer> pitStatus = new HashMap<>();

		try {

			Kalah kalah = kalahService.playTurn(gameId, pitId);

			populatePitStatus(pitStatus, kalah.getPits());

			Link uri = linkTo(methodOn(KalahController.class).startGame()).slash(kalah.getGameId()).withSelfRel();

			response.put(KalahConstants.ID, kalah.getGameId());
			response.put(KalahConstants.URI, uri.toUri());
			response.put(KalahConstants.STATUS, pitStatus);

		} catch (KalahServiceException kalahServiceException) {
			
			log.error("Exception while executing Turn", kalahServiceException);
			return new ResponseEntity<Object>(kalahServiceException.getMessage(), HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	/**
	 * This method generates the response from kalah pits array
	 * 
	 * @param pitStatus hashmap to generate the pit Status Json as expected
	 * @param pits      pits of the kalah
	 * @return response populated from pits
	 */
	private Map<Integer, Integer> populatePitStatus(Map<Integer, Integer> pitStatus, int[] pits) {

		IntStream.range(0, pits.length).forEachOrdered(pitId -> pitStatus.put(pitId + 1, pits[pitId]));

		return pitStatus;

	}

}
