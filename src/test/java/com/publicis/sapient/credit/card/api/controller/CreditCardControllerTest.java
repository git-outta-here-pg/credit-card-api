package com.publicis.sapient.credit.card.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publicis.sapient.credit.card.api.component.Server;
import com.publicis.sapient.credit.card.api.dto.CardRequestBody;
import com.publicis.sapient.credit.card.api.dto.CardResponseBody;
import com.publicis.sapient.credit.card.api.model.CardProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

	@Autowired
	private MockMvc mvc;
	private CardRequestBody CARDREQUESTBODY;
	private CardRequestBody INVALIDCARDREQUESTBODY;
	private CardRequestBody NULLCARDREQUESTBODY;
	private ObjectMapper objectMapper;

	@MockBean
	private Server serverMock;

	@Before
	public void setUp() {
		objectMapper = Jackson2ObjectMapperBuilder.json().build();
		CARDREQUESTBODY = new CardRequestBody("5196081888500645", "John Smith", CardProcessor.MASTERCARD, "12/22", 344,
				new BigDecimal(2000.00));
		INVALIDCARDREQUESTBODY = new CardRequestBody("5196081888500645", "", CardProcessor.MASTERCARD, "12/22", 344,
				new BigDecimal(2000.00));
		NULLCARDREQUESTBODY = null;
	}

	@Test
	public void givenValidCreditCardRequest_thenReturn_201() throws Exception {
		String creditCardRequestJson = objectMapper.writeValueAsString(CARDREQUESTBODY);

		when(serverMock.saveCardDetails(any(CardRequestBody.class))).thenReturn(true);

		MvcResult result = mvc
				.perform(post("/api/v1/card").contentType(MediaType.APPLICATION_JSON).content(creditCardRequestJson))
				.andExpect(status().isCreated()).andReturn();

		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

		boolean response = Boolean.parseBoolean(result.getResponse().getContentAsString());
		assertThat(response).isTrue();
	}

	@Test
	public void givenInvalidCreditCardRequest_thenReturn_400() throws Exception {
		String creditCardRequestJson = objectMapper.writeValueAsString(INVALIDCARDREQUESTBODY);

		MvcResult result = mvc
				.perform(post("/api/v1/card").contentType(MediaType.APPLICATION_JSON).content(creditCardRequestJson))
				.andExpect(status().isBadRequest()).andReturn();

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}

	@Test
	public void givenNullCreditCardRequest_thenReturn_400() throws Exception {
		String creditCardRequestJson = objectMapper.writeValueAsString(NULLCARDREQUESTBODY);

		MvcResult result = mvc
				.perform(post("/api/v1/card").contentType(MediaType.APPLICATION_JSON).content(creditCardRequestJson))
				.andExpect(status().isBadRequest()).andReturn();

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}

	@Test
	public void givenCreditCards_whenGetCards_thenReturnJsonArray() throws Exception {
		CardResponseBody cardResponseBody = new CardResponseBody("5196081888500645", "John Smith",
				CardProcessor.MASTERCARD, new BigDecimal(1500.00), new BigDecimal(2000.00));

		List<CardResponseBody> allCards = Arrays.asList(cardResponseBody);

		given(serverMock.getAllCards()).willReturn(allCards);

		mvc.perform(get("/api/v1/cards").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].creditCardNumber", is(cardResponseBody.getCreditCardNumber())));
	}

	@Test
	public void givenNoCreditCards_whenGetCards_thenReturnJsonArray() throws Exception {
		given(serverMock.getAllCards()).willReturn(Collections.emptyList());

		mvc.perform(get("/api/v1/cards").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

}
