package com.sstixbackend.response;

public record UsersLoginResponse(
		String token,
		Integer level
) {
}