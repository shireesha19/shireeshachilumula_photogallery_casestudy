package com.shireesha.casestudy.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String>{
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", 
			Pattern.CASE_INSENSITIVE);
	
	public static boolean  validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
	
	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext context) {
		return (validate(arg0) && (arg0.length() > 8) && (arg0.length() < 14));
	}
}
