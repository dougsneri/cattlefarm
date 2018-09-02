package br.com.igorrodrigues.cattlefarm.Validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.BovineType;
import br.com.igorrodrigues.cattlefarm.models.flock.Sex;

public class BovineValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Bovine.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "id", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "birth", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "weight", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "sex", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "type", "field.required");

		Bovine bovine = (Bovine) target;
		if (bovine.getSex() == Sex.FEMALE
				&& (bovine.getType() == BovineType.BOI || bovine.getType() == BovineType.TOURO)) {
			errors.rejectValue("type", "field.invalid.sex");
		}
		if (bovine.getSex() == Sex.MALE && bovine.getType() == BovineType.VACA) {
			errors.rejectValue("type", "field.invalid.sex");
		}
	}

}
