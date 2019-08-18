package com.matching.segmentsmatching.rest;

/**
 * 
 * Validation error. Represents JSON response.
 * 
 * @author Etnetera
 *
 */
public class ValidationError {

	private String field;
	private String message;

	public ValidationError() {
	}

	public ValidationError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
