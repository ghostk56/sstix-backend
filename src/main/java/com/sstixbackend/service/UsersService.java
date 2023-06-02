package com.sstixbackend.service;

import java.util.Optional;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sstixbackend.model.Users;
import com.sstixbackend.repository.UsersRepository;
import com.sstixbackend.request.UsersLoginRequest;
import com.sstixbackend.request.UsersRegisterRequest;
import com.sstixbackend.request.UsersUpdateRequest;
import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.response.UsersInfoResponse;
import com.sstixbackend.util.JWTUtil;
import com.sstixbackend.util.SHAUtil;

@Service
@Transactional
public class UsersService {
	@Autowired
	private UsersRepository us;

	@Autowired
	private JWTUtil jwt;

	@Autowired
	private SHAUtil sha;

	private Optional<Users> findById;

	public ResponseEntity<RestfulResponse<?>> login(UsersLoginRequest user) {
		if (verification(user)) {
			String userName = user.userName();
			Users ou = us.findByUserName(userName);
			String token = jwt.generateToken(ou);
			RestfulResponse<String> response = new RestfulResponse<String>("00000", "登入成功", token);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00001", "登入失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public boolean verification(UsersLoginRequest user) {
		if (user != null && user.userName() != null) {
			String userName = user.userName();
			Users original = us.findByUserName(userName);
			if (original != null) {
				String sp = sha.getResult(user.password());
				return original.getPassword().equals(sp);
			}
		}
		return false;
	}

	public ResponseEntity<RestfulResponse<?>> validateToken(String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00000", "驗證成功", null);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	public ResponseEntity<RestfulResponse<?>> userInfo(String auth) {
		String token = auth.substring(6);
		Integer id;
		try {
			id = jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		Optional<Users> optional = us.findById(id);
		if (optional.isPresent()) { 
			Users user = optional.get();
			UsersInfoResponse infoResponse = new UsersInfoResponse(user.getUserName(), user.getEmail(), user.getPhone());
			RestfulResponse<UsersInfoResponse> response = new RestfulResponse<UsersInfoResponse>("00000", "成功", infoResponse);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00006", "資料錯誤", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> addUser(UsersRegisterRequest rq) {
		if (rq != null && rq.userName()!= null) {
			Users original = us.findByUserName(rq.userName());
			if (original != null) {
				RestfulResponse<String> response = new RestfulResponse<String>("00003", "帳號已存在", null);
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			}
			String password = sha.getResult(rq.password());
			Users user = Users.builder().userName(rq.userName()).email(rq.email()).phone(rq.phone()).password(password)
					.level(1).status(1).build();
			us.save(user);
			String token = jwt.generateToken(user);
			RestfulResponse<String> response = new RestfulResponse<String>("00000", "註冊成功", token);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00002", "註冊失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> updateUser(UsersUpdateRequest user, String auth) {
		String token = auth.substring(6);
		Integer id;
		try {
			id = jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		if (user != null && id != null) {
			Optional<Users> optional = us.findById(id);
			if (optional.isPresent()) {
				Users original = optional.get();
				if (sha.getResult(user.oldPassword()).equals(original.getPassword())) {
					String sp = sha.getResult(user.password());
					original.setPassword(sp);
					RestfulResponse<String> response = new RestfulResponse<String>("00000", "修改成功", token);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
			}
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00005", "修改失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
}
