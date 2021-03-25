package com.publicis.sapient.credit.card.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.publicis.sapient.credit.card.api.component.Server;
import com.publicis.sapient.credit.card.api.dto.CardRequestBody;
import com.publicis.sapient.credit.card.api.dto.CardResponseBody;
import com.publicis.sapient.credit.card.api.dto.PostResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * @author poonamgupta
 * This controller has two end points for 1 -> to add a new card into the system
 * 2 -> to get all the credit cards
 */
@Slf4j
@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class CreditCardController {
	private final Server server;

	
	@PostMapping(value = "/card", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostResponse> pushData(@Valid @RequestBody CardRequestBody cardRequestBody) {

		log.info("Card information received.");
		PostResponse postResponse = new PostResponse();
		if (server.saveCardDetails(cardRequestBody)) {
			postResponse.setResponse("Card added successfully");
		} else {
			postResponse.setResponse("Card was not added");
		}
		return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
	}

	@GetMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CardResponseBody>> getData() throws IOException {

		log.info("Get the list of all cards.");

		List<CardResponseBody> responseList = server.getAllCards();

		return ResponseEntity.ok(responseList);
	}

}
