package com.publicis.sapient.credit.card.api.model;

/**
 * @author poonamgupta
 *
 */
public enum CardProcessor {
	VISA("visa"), MASTERCARD("mastercard"), AMEX("amex");

	private String cardProcessor;

	CardProcessor(String cardProcessor) {
        this.cardProcessor = cardProcessor;
    }

	public String getCardProcessorString() {
		return cardProcessor;
	}

}
