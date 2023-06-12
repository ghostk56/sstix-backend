package com.sstixbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sstixbackend.model.Events;
import com.sstixbackend.model.Orders;
import com.sstixbackend.model.Users;
import com.sstixbackend.repository.EventsRepository;
import com.sstixbackend.repository.OrdersRepository;
import com.sstixbackend.repository.UsersRepository;
import com.sstixbackend.request.OrderSaveRequest;
import com.sstixbackend.request.OrderUpdateRequest;
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
	private UsersRepository ur;

	@Autowired
	private JWTUtil jwt;

	public ResponseEntity<RestfulResponse<?>> selectAllorders(String auth) {
		String token = auth.substring(6);
		Integer id;
		try {
			id = jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		Optional<Users> optional = ur.findById(id);
		if (optional.isPresent()) {
			Users user = optional.get();
			if (user.getLevel() != 2) {
				RestfulResponse<String> response = new RestfulResponse<String>("00005", "沒有管理員權限", null);
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			}
		}

		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<Orders> orders = or.findAll(sort);
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

	public ResponseEntity<RestfulResponse<?>> selectUserOrders(String auth) {
		String token = auth.substring(6);
		Integer userId;
		try {
			userId = jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		if (userId != null) {
			Sort sort = Sort.by(Sort.Direction.DESC, "id");
			List<Orders> orders = or.findByUsersId(userId, sort);
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
			RestfulResponse<String> response = new RestfulResponse<String>("00024", "查無資料", null);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00026", "資料錯誤", null);
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
				if (event.getStatus() != 1) {
					RestfulResponse<String> response = new RestfulResponse<String>("00022", "未開賣", null);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
				Integer quantity = req.quantity();
				Integer qty = event.getQty();
				if (quantity > qty) {
					RestfulResponse<String> response = new RestfulResponse<String>("00021", "剩餘票數不足", null);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
				event.setQty(qty - quantity);
				Orders order = Orders.builder().usersId(userId).events(event).quantity(req.quantity())
						.eventPrice(event.getPrice()).Status(1).build();
				or.save(order);
				RestfulResponse<String> response = new RestfulResponse<String>("00000", "訂購成功", null);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00023", "訂購失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> updateOrder(OrderUpdateRequest rq, String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		if (rq != null && rq.orderId() != null) {
			Optional<Orders> optional = or.findById(rq.orderId());
			if (optional.isPresent()) {
				Orders order = optional.get();
				order.setStatus(rq.status());
				RestfulResponse<String> response = new RestfulResponse<String>("00000", "修改成功", null);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00025", "修改失敗", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
}
