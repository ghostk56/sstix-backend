package com.sstixbackend.request;

public record UsersLoginRequest(
        String userName,
        String password
) {
}