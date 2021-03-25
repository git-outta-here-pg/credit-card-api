package com.publicis.sapient.credit.card.api.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.publicis.sapient.credit.card.api.validator.CustomValidations.CardNumberValidator;

/**
 * @author poonamgupta
 *
 */
public class LuhnValidator implements ConstraintValidator<CardNumberValidator, String> {

	@Override
	public boolean isValid(String cardNumber, ConstraintValidatorContext context) {

		int[] cardNumberIntArray = new int[cardNumber.length()];

		for (int i = 0; i < cardNumber.length(); i++) {
			char c = cardNumber.charAt(i);
			try {
				cardNumberIntArray[i] = Integer.parseInt("" + c);
			} catch (NumberFormatException nfe) {
				return false;
			}
		}

		for (int i = cardNumberIntArray.length - 2; i >= 0; i = i - 2) {
			int num = cardNumberIntArray[i];
			num = num * 2;
			if (num > 9) {
				num = num % 10 + num / 10;
			}
			cardNumberIntArray[i] = num;
		}

		if (sumDigits(cardNumberIntArray) % 10 == 0) {
			return true;
		}

		return false;
	}

	private int sumDigits(int[] arr) {
		return Arrays.stream(arr).sum();
	}

}
