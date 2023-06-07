package com.sstixbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sstixbackend.model.Events;
import com.sstixbackend.model.Orders;
import com.sstixbackend.repository.EventsRepository;
import com.sstixbackend.repository.OrdersRepository;
import com.sstixbackend.request.EventsUpdateRequest;
import com.sstixbackend.request.OrderSaveRequest;
import com.sstixbackend.response.EventsSelectResponse;
import com.sstixbackend.response.OrdersSelectResponse;
import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.util.JWTUtil;

@Service
@Transactional
public class OrdersService {
	@Autowired
	private EventsRepository er;

	@Autowired
	private OrdersRepository or;

	@Autowired
	private JWTUtil jwt;

	public ResponseEntity<RestfulResponse<?>> selectAllorders(String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		List<Orders> orders = or.findAll();
		if (orders != null) {
			List<OrdersSelectResponse> responseList = orders.stream().map(order -> {
				Events event = order.getEvents();
				EventsSelectResponse selectResponse = new EventsSelectResponse(event.getId(), event.getName(),
						event.getDetails(), event.getLocation(), event.getOrganizer(), event.getEventDate(),
						event.getStatus(), event.getPrice(), event.getQty(), event.getImage1());

				OrdersSelectResponse response = new OrdersSelectResponse(order.getId(), order.getUsersId(),
						selectResponse, order.getQuantity(), order.getEventPrice(), order.getOrderDate(),
						order.getStatus());
				return response;
			}).collect(Collectors.toList());

			RestfulResponse<List<OrdersSelectResponse>> response = new RestfulResponse<List<OrdersSelectResponse>>(
					"00000", "成功", responseList);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00026", "資料錯誤", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> selectEvents(Integer id) {
		if (id != null) {
			Optional<Events> optional = er.findById(id);
			if (optional.isPresent()) {
				Events event = optional.get();
				EventsSelectResponse selectResponse = new EventsSelectResponse(event.getId(), event.getName(),
						event.getDetails(), event.getLocation(), event.getOrganizer(), event.getEventDate(),
						event.getStatus(), event.getPrice(), event.getQty(), event.getImage1());

				RestfulResponse<EventsSelectResponse> response = new RestfulResponse<EventsSelectResponse>("00000",
						"成功", selectResponse);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00016", "資料錯誤", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> saveOrder(OrderSaveRequest req, String auth) {
		String token = auth.substring(6);
		Integer userId;
		try {
			userId = jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		if (req != null && userId != null) {
			Optional<Events> optional = er.findById(req.eventsId());
			if (optional.isPresent()) {
				Events event = optional.get();
				if (req.quantity() > event.getQty()) {
					RestfulResponse<String> response = new RestfulResponse<String>("00021", "剩餘票數不足", null);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
				Orders order = Orders.builder().usersId(userId).events(event).quantity(req.quantity())
						.eventPrice(event.getPrice()).Status(1).build();
				or.save(order);
				RestfulResponse<String> response = new RestfulResponse<String>("00000", "訂購成功", null);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00022", "訂購失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> updateEvents(EventsUpdateRequest request, String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		if (request != null && request.id() != null) {
			Optional<Events> optional = er.findById(request.id());
			if (optional.isPresent()) {
				Events event = optional.get();
				event.setName(request.name());
				event.setDetails(request.details());
				event.setLocation(request.location());
				event.setOrganizer(request.organizer());
				event.setStatus(request.status());
				event.setPrice(request.price());
				event.setQty(request.qty());
				event.setEventDate(request.eventDate());
				if (request.image1() != null && !request.image1().equals(""))
					event.setImage1(request.image1());
				RestfulResponse<String> response = new RestfulResponse<String>("00000", "修改成功", null);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00015", "修改失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
}
