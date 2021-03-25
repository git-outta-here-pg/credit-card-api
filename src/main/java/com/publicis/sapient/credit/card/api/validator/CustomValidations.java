package com.publicis.sapient.credit.card.api.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.publicis.sapient.credit.card.api.model.CardProcessor;
import com.publicis.sapient.credit.card.api.util.ErrorsAndExceptions;

/**
 * @author poonamgupta
 *
 */
public class CustomValidations {

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RUNTIME)
	@Documented
	@Constraint(validatedBy = CardProcessorValidator.class)
	public @interface CardProcessorSubset {
		CardProcessor[] anyOf();
	    String message() default "must be any of {anyOf}";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	}
	
	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	  @Constraint(validatedBy = {LuhnValidator.class})
	  @Retention(RetentionPolicy.RUNTIME)
	  public @interface CardNumberValidator {
	    String message()
	    default ErrorsAndExceptions.INVALID_CARD_NUMBER;
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	  }
}
