package com.sstixbackend.request;

public record UsersUpdateRequest(
        String password,
        String oldPassword
) {
}