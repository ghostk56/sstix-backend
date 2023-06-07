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
import org.springframework.web.bind.annotation.RestController;

import com.sstixbackend.request.EventsUpdateRequest;
import com.sstixbackend.request.OrderSaveRequest;
import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.service.EventsService;
import com.sstixbackend.service.OrdersService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private EventsService es;

	@Autowired
	private OrdersService os;

	@GetMapping
	public ResponseEntity<RestfulResponse<?>> selectAllEvent(@RequestHeader("Authorization") String auth) {
		return os.selectAllorders(auth);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestfulResponse<?>> selectEvent(@PathVariable Integer id) {
		return es.selectEvents(id);
	}

	@PostMapping
	public ResponseEntity<RestfulResponse<?>> addOrder(@RequestHeader("Authorization") String auth,
			@RequestBody OrderSaveRequest rq) {
		return os.saveOrder(rq, auth);
	}

	@PutMapping
	public ResponseEntity<RestfulResponse<?>> updateEvent(@RequestHeader("Authorization") String auth,
			@RequestBody EventsUpdateRequest rq) {
		return es.updateEvents(rq, auth);
	}
}
