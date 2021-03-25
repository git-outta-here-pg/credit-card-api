package com.publicis.sapient.credit.card.api.dto;

import java.math.BigDecimal;

import com.publicis.sapient.credit.card.api.model.CardProcessor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author poonamgupta
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardResponseBody {
	private String creditCardNumber;

	private String cardHolderName;

	private CardProcessor creditCardProcessor;
	
	private BigDecimal creditCardBalance;
	
	private BigDecimal creditCardLimit;
}
