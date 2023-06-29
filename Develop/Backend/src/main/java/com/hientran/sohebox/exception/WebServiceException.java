package com.hientran.sohebox.exception;

/**
 * Exception from bittrex services
 *
 * @author hientran
 */
public class WebServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public WebServiceException(String message) {
		super(message);
	}

	public WebServiceException(String message, String reponseBody) {
		super(message + '\n' + reponseBody);
	}
}
