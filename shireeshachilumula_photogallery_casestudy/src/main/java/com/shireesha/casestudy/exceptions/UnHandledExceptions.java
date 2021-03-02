package com.shireesha.casestudy.exceptions;

public class UnHandledExceptions extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnHandledExceptions(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
