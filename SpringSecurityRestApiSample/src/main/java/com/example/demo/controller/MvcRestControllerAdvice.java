package com.example.demo.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MvcRestControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<Object> SQLIntegrityConstraintViolationExceptionHandler(
			SQLIntegrityConstraintViolationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
