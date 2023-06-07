package com.sstixbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sstixbackend.request.EventsSaveRequest;
import com.sstixbackend.request.EventsUpdateRequest;
import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.service.EventsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/events")
public class EventsController {

	@Autowired
	private EventsService es;

	@GetMapping
	public ResponseEntity<RestfulResponse<?>> selectAllEvent(
			@RequestParam(required = false) String name, @RequestParam(required = false) Integer status) {
		return es.selectAllEvents(name, status);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestfulResponse<?>> selectEvent(
			@PathVariable Integer id) {
		return es.selectEvents(id);
	}

	@PostMapping
	public ResponseEntity<RestfulResponse<?>> addEvent(@RequestHeader("Authorization") String auth,
			@RequestBody EventsSaveRequest rq) {
		return es.saveEvents(rq, auth);
	}

	@PutMapping
	public ResponseEntity<RestfulResponse<?>> updateEvent(@RequestHeader("Authorization") String auth,
			@RequestBody EventsUpdateRequest rq) {
		return es.updateEvents(rq, auth);
	}
}
