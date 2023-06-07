package com.sstixbackend.request;

public record OrderSaveRequest(
		Integer eventsId,
        Integer quantity
) {
}