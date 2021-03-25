package com.publicis.sapient.credit.card.api.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.publicis.sapient.credit.card.api.model.CardProcessor;
import com.publicis.sapient.credit.card.api.validator.CustomValidations.CardProcessorSubset;

/**
 * @author poonamgupta
 *
 */
public class CardProcessorValidator implements ConstraintValidator<CardProcessorSubset, CardProcessor> {
	private CardProcessor[] subset;

	@Override
	public void initialize(CardProcessorSubset constraint) {
		this.subset = constraint.anyOf();
	}

	@Override
	public boolean isValid(CardProcessor value, ConstraintValidatorContext context) {
		return value != null || Arrays.asList(subset).contains(value);
	}
}