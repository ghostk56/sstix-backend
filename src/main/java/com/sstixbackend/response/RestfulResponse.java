package com.sstixbackend.response;

public record RestfulResponse<T> (
		String returnCode,
		String returnMsg,
		T data
) {
}

