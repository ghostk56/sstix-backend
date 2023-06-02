package com.sstixbackend.response;

public record UsersInfoResponse(
		String userName,
		String email,
		String phone
) {
}