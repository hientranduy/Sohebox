package com.hientran.sohebox.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class APIResponse<Data> {

	private HttpStatus status;

	private String message;

	private List<String> errors;

	private Data data;

	/**
	 * Constructor
	 *
	 */
	public APIResponse() {
		super();
	}

	/**
	 * 
	 * Constructor
	 *
	 * @param status
	 * @param message
	 * @param errors
	 */
	public APIResponse(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	/**
	 * 
	 * Constructor
	 *
	 * @param status
	 * @param message
	 * @param error
	 */
	public APIResponse(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}

	/**
	 * 
	 * Constructor
	 *
	 * @param status
	 * @param message
	 */
	public APIResponse(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	/**
	 * 
	 * Constructor
	 *
	 * @param status
	 * @param errors
	 */
	public APIResponse(HttpStatus status, List<String> errors) {
		super();
		this.status = status;
		this.errors = errors;
	}

	/**
	 * Get status
	 *
	 * @return status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Set status
	 *
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * Get message
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set message
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get errors
	 *
	 * @return errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * Set errors
	 *
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * Get data
	 *
	 * @return data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * Set data
	 *
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "APIResponse [status=" + status + ", message=" + message + ", errors=" + errors + ", data=" + data + "]";
	}
}
