package com.publicis.sapient.credit.card.api.component.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.publicis.sapient.credit.card.api.component.Server;
import com.publicis.sapient.credit.card.api.dto.CardRequestBody;
import com.publicis.sapient.credit.card.api.dto.CardResponseBody;
import com.publicis.sapient.credit.card.api.model.CreditCardEntity;
import com.publicis.sapient.credit.card.api.model.Status;
import com.publicis.sapient.credit.card.api.model.UserEntity;
import com.publicis.sapient.credit.card.api.service.CardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

/**
 * @author poonamgupta
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServerImpl implements Server {

	private final CardService cardServiceImpl;
	private final ModelMapper modelMapper;

	@Override
	public boolean saveCardDetails(CardRequestBody cardRequestBody) {
		log.info("Card information mapped to CreditCardEntity.");
		CreditCardEntity cardEntity = modelMapper.map(cardRequestBody, CreditCardEntity.class);
		cardEntity.setCardBalance(new BigDecimal(0.0));
		cardEntity.setCardStatus(Status.ACTIVE);

		UserEntity userEntity = new UserEntity();
		userEntity.setName(cardRequestBody.getCardHolderName());
		userEntity.setUserStatus(Status.ACTIVE);
		cardEntity.setUser(userEntity);

		return cardServiceImpl.saveCardData(cardEntity);
	}

	@Override
	public List<CardResponseBody> getAllCards() {
		log.info("Create CardResponseBody list.");
		Optional<List<CreditCardEntity>> creditCards = cardServiceImpl.getAllCards();
		if (creditCards.isPresent()) {
			List<CardResponseBody> cardResponseList = new ArrayList<>();
			for (CreditCardEntity creditCardEntity : creditCards.get()) {
				cardResponseList.add(new CardResponseBody(creditCardEntity.getCardNumber(),
						creditCardEntity.getUser().getName(), creditCardEntity.getCardProcessor(),
						creditCardEntity.getCardBalance(), creditCardEntity.getCardLimit()));
			}
			return cardResponseList;
		}
		return Collections.emptyList();
	}

}
