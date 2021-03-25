package com.publicis.sapient.credit.card.api.util;

/**
 * @author poonamgupta
 *
 */
public class ErrorsAndExceptions {

	private ErrorsAndExceptions() {
		throw new IllegalStateException("Utility class - ErrorsAndExceptions");
	}

	public static final String INVALID_INPUT = "cannot be empty or null";

	public static final String INVALID_CARD_NUMBER = "Credit card number is invalid";

	public static final String INVALID_CARD_LIMIT = "Card limit must be greater than 100 GBP";

	public static final String INVALID_CODE = "Credit card code is invalid";

	public static final String INVALID_CARD_PROCESSOR = "Card Processor not supported";

}
