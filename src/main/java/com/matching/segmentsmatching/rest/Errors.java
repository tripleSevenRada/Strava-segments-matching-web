package com.matching.segmentsmatching.rest;

import java.util.List;

/**
 * 
 * Envelope for the validation errors. Represents JSON response.
 * 
 * @author Etnetera
 *
 */
public class Errors {

	private List<ValidationError> errors;

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}

}
