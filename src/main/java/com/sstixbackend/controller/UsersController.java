package com.sstixbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sstixbackend.request.UsersLoginRequest;
import com.sstixbackend.request.UsersRegisterRequest;
import com.sstixbackend.request.UsersUpdateRequest;
import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.service.UsersService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService us;

	@GetMapping
	public ResponseEntity<RestfulResponse<?>> userInfo(@RequestHeader("Authorization") String auth) {
		return us.userInfo(auth);
	}

	@PostMapping("/validate")
	public ResponseEntity<RestfulResponse<?>> validateToken(@RequestHeader("Authorization") String auth) {
		return us.validateToken(auth);
	}

	@PostMapping("/login")
	public ResponseEntity<RestfulResponse<?>> login(@RequestBody UsersLoginRequest rq) {
		return us.login(rq);
	}

	@PostMapping
	public ResponseEntity<RestfulResponse<?>> register(@RequestBody UsersRegisterRequest rq) {
		return us.addUser(rq);
	}

	@PutMapping
	public ResponseEntity<RestfulResponse<?>> update(@RequestHeader("Authorization") String auth,
			@RequestBody UsersUpdateRequest rq) {
		return us.updateUser(rq, auth);
	}

}
