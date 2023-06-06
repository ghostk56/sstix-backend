package com.sstixbackend.request;

public record EventsSaveRequest(
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