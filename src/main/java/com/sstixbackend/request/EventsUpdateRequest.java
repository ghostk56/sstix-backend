package com.sstixbackend.request;

public record EventsUpdateRequest(
		Integer id,
        String name,
        String details,
        String location,
        String organizer,
        String eventDate,
        Integer status,
        Integer price,
        Integer qty,
        String image1
) {
}