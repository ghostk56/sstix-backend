package com.sstixbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sstixbackend.aop.BaseException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/test")
public class TestAopController extends BaseException {

	@PostMapping
	public ResponseEntity<String> test(@RequestHeader("Authorization") String auth) {
		System.out.println("testController");
		if (auth.length() > 10) {
			throw new RuntimeException("test拋出錯誤");
		}
		return ResponseEntity.status(HttpStatus.OK).body("成功");
	}

//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<String> handleRuntimeException(Exception error) {
//		System.out.println("handleRuntimeExceptionTest");
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
//	}
//	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleException(Exception error) {
//		System.out.println("handleExceptionTest");
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
//	}

}
