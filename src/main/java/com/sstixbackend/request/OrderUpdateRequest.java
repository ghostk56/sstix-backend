package com.sstixbackend.request;

public record OrderUpdateRequest(
		Integer orderId,
		Integer status
) {
}