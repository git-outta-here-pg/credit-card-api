package com.publicis.sapient.credit.card.api.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author poonamgupta
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

	private LocalDateTime localDateTime;
	private String message;
	private List<String> errors;
}