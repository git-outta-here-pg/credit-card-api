package com.publicis.sapient.credit.card.api.service;

import java.util.List;
import java.util.Optional;

import com.publicis.sapient.credit.card.api.model.CreditCardEntity;

/**
 * @author poonamgupta
 *
 */
public interface CardService {

	public boolean saveCardData(CreditCardEntity cardEntity);
	public Optional<List<CreditCardEntity>> getAllCards();
}
