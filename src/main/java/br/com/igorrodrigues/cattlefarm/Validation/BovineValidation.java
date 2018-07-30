package br.com.igorrodrigues.cattlefarm.Validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.igorrodrigues.cattlefarm.models.Bovine;

public class BovineValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Bovine.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "id", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "nick", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "birth", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "weight", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "sex", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "type", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "status", "field.required");
		
	}

}
