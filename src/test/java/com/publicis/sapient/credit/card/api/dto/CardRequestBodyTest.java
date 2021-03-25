package com.publicis.sapient.credit.card.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.publicis.sapient.credit.card.api.model.CardProcessor;

public class CardRequestBodyTest {
	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void givenCardRequestBody_hasEmptyCardNumber_thenReportError() {
		CardRequestBody model = new CardRequestBody("", "John Smith", CardProcessor.MASTERCARD, "12/22", 344,
				new BigDecimal(2000.00));

		Set<ConstraintViolation<CardRequestBody>> constraintViolations = validator.validate(model);

		assertEquals(2, constraintViolations.size());

	}

	@Test
	public void givenCardRequestBody_hasAlphaNumericCardNumber_thenReportError() {
		CardRequestBody model = new CardRequestBody("RRRRR888500645", "Jones Jill", CardProcessor.MASTERCARD, "12/22",
				344, new BigDecimal(2000.00));

		Set<ConstraintViolation<CardRequestBody>> constraintViolations = validator.validate(model);

		assertEquals(1, constraintViolations.size());

	}

	@Test
	public void givenCardRequestBody_hasCardNumberWith20Chars_thenReportError() {
		CardRequestBody model = new CardRequestBody("51960818885006459099", "Jones Jill", CardProcessor.MASTERCARD,
				"12/22", 344, new BigDecimal(2000.00));

		Set<ConstraintViolation<CardRequestBody>> constraintViolations = validator.validate(model);
		// the size of violation list is 2 as it is an invalid card number and the
		// length > 10
		assertEquals(2, constraintViolations.size());

	}

	@Test
	public void givenCardRequestBody_hasNullNameField_thenReportError() {
		CardRequestBody model = new CardRequestBody("5196081888500645", null, CardProcessor.MASTERCARD, "12/22", 344,
				new BigDecimal(2000.00));

		Set<ConstraintViolation<CardRequestBody>> constraintViolations = validator.validate(model);

		assertEquals(1, constraintViolations.size());

	}

	@Test
	public void givenCardRequestBody_hasCreditLimitLessThan100_thenReportError() {
		CardRequestBody model = new CardRequestBody("5196081888500645", "Jones Jill", CardProcessor.MASTERCARD, "12/22",
				344, new BigDecimal(98.00));

		Set<ConstraintViolation<CardRequestBody>> constraintViolations = validator.validate(model);

		assertEquals(1, constraintViolations.size());

	}

	@Test
	public void givenValidCardRequestBody_thenNoReportError() {
		CardRequestBody model = new CardRequestBody("6250947000000014", "Jones Jill", CardProcessor.MASTERCARD, "12/22",
				344, new BigDecimal(2000.00));

		Set<ConstraintViolation<CardRequestBody>> constraintViolations = validator.validate(model);

		assertEquals(0, constraintViolations.size());

	}
}