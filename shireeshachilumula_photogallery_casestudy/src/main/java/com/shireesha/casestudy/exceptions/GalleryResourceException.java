package com.shireesha.casestudy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GalleryResourceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GalleryResourceException() {
        super();
    }
    public GalleryResourceException(HttpStatus value, String message, Throwable cause) {
        super(message, cause);
    }
    public GalleryResourceException(String message, Throwable cause) {
        super(message, cause);
    }
    public GalleryResourceException(String message) {
        super(message);
    }
    public GalleryResourceException(Throwable cause) {
        super(cause);
    }
}