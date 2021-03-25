package com.publicis.sapient.credit.card.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.publicis.sapient.credit.card.api.model.CardProcessor;
import com.publicis.sapient.credit.card.api.util.ErrorsAndExceptions;
import com.publicis.sapient.credit.card.api.validator.CustomValidations.CardNumberValidator;
import com.publicis.sapient.credit.card.api.validator.CustomValidations.CardProcessorSubset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author poonamgupta
 *
 */
@JsonSerialize(as = CardRequestBody.class)
@JsonDeserialize(as = CardRequestBody.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardRequestBody {

	@NotBlank(message = ErrorsAndExceptions.INVALID_INPUT)
	@Size(min = 13, max = 19, message = ErrorsAndExceptions.INVALID_CARD_NUMBER)
	@CardNumberValidator
	private String creditCardNumber;

	@NotBlank(message = ErrorsAndExceptions.INVALID_INPUT)
	private String cardHolderName;

	@NotNull 
	@CardProcessorSubset(anyOf = { CardProcessor.VISA, CardProcessor.AMEX,
			CardProcessor.MASTERCARD }, message = ErrorsAndExceptions.INVALID_CARD_PROCESSOR)
	private CardProcessor creditCardProcessor;

	@NotBlank(message = ErrorsAndExceptions.INVALID_INPUT)
	private String creditCardExpiry;

	@NotNull(message = ErrorsAndExceptions.INVALID_INPUT)
	private Integer code;

	@DecimalMin(value = "100", inclusive = false, message = ErrorsAndExceptions.INVALID_CARD_LIMIT)
	private BigDecimal creditCardLimit;
}
