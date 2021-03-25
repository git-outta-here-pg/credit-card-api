package com.publicis.sapient.credit.card.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.publicis.sapient.credit.card.api.model.CreditCardEntity;
import com.publicis.sapient.credit.card.api.repository.CardStoreRepository;
import com.publicis.sapient.credit.card.api.service.CardService;

import lombok.RequiredArgsConstructor;

/**
 * @author poonamgupta
 *
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
	
	private final CardStoreRepository cardStoreRepository;

	@Override
	public boolean saveCardData(CreditCardEntity cardEntity) {	
		return (cardStoreRepository.save(cardEntity) != null) ? true : false;		
	}

	@Override
	public Optional<List<CreditCardEntity>> getAllCards() {
		return Optional.ofNullable(cardStoreRepository.findAll());
	}
	
}
