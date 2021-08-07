package com.example.demo.domain.service.exception;

public class SalesIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4202359667560878738L;

	public SalesIdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SalesIdNotFoundException(String message) {
		super(message);
	}

}
