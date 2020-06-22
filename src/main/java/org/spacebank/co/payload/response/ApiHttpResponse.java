package org.spacebank.co.payload.response;

import org.springframework.http.HttpStatus;

public class ApiHttpResponse {
	private HttpStatus status;
	private Boolean success;
	private String message;
	

	public ApiHttpResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	public ApiHttpResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ApiHttpResponse(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
