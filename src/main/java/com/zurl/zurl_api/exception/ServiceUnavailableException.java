package com.zurl.zurl_api.exception;

public class ServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public ServiceUnavailableException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
