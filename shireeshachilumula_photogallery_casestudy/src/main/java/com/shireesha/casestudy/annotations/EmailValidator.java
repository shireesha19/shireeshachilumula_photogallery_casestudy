package com.shireesha.casestudy.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String>{
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	
	@Override
	public void initialize(EmailConstraint constraintAnnotation) {}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return validateEmail(email);
	}
	
	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
//	  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
//	  Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",
//	  Pattern.CASE_INSENSITIVE);
//	  
//	  public static boolean validate(String emailStr) { Matcher matcher =
//	  VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr); return matcher.find(); }
//	  
//	  @Override public boolean isValid(String arg0, ConstraintValidatorContext
//	  context) { return (validate(arg0) && (arg0.length() > 8) && (arg0.length() <
//	  14)); }
	 
}
