package com.sstixbackend.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseException {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(Exception error) {
		System.out.println("handleRuntimeException");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception error) {
		System.out.println("handleException");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
	}
	
}
