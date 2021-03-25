package com.publicis.sapient.credit.card.api.component;

import java.util.List;

import com.publicis.sapient.credit.card.api.dto.CardRequestBody;
import com.publicis.sapient.credit.card.api.dto.CardResponseBody;

/**
 * @author poonamgupta
 *
 */
public interface Server {
	public boolean saveCardDetails(CardRequestBody cardRequestBody);
	public List<CardResponseBody> getAllCards();
}
