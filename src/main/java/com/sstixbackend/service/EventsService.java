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
import com.sstixbackend.repository.EventsRepository;
import com.sstixbackend.request.EventsSaveRequest;
import com.sstixbackend.request.EventsUpdateRequest;
import com.sstixbackend.response.EventsSelectResponse;
import com.sstixbackend.response.RestfulResponse;
import com.sstixbackend.util.JWTUtil;

@Service
@Transactional
public class EventsService {
	@Autowired
	private EventsRepository er;

	@Autowired
	private JWTUtil jwt;

	public ResponseEntity<RestfulResponse<?>> selectAllEvents(String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<Events> events = er.findAll(sort);
		if (events != null) {
			List<EventsSelectResponse> responseList = events.stream().map(event -> {
				EventsSelectResponse response = new EventsSelectResponse(event.getId(), event.getName(),
						event.getDetails(), event.getLocation(), event.getOrganizer(), event.getEventDate(),
						event.getStatus(), event.getPrice(), event.getQty(), event.getImage1());
				return response;
			}).collect(Collectors.toList());

			RestfulResponse<List<EventsSelectResponse>> response = new RestfulResponse<List<EventsSelectResponse>>(
					"00000", "成功", responseList);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00016", "資料錯誤", null);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}

	public ResponseEntity<RestfulResponse<?>> selectEvents(Integer id, String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
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

	public ResponseEntity<RestfulResponse<?>> saveEvents(EventsSaveRequest request, String auth) {
		String token = auth.substring(6);
		try {
			jwt.validateToken(token);
		} catch (AuthException e) {
			RestfulResponse<String> response = new RestfulResponse<String>("00004", e.getMessage(), null);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		if (request != null) {
			Events event = Events.builder().name(request.name()).details(request.details()).location(request.location())
					.organizer(request.organizer()).status(request.status()).price(request.price()).qty(request.qty())
					.eventDate(request.eventDate()).image1(request.image1()).build();
			er.save(event);
			RestfulResponse<String> response = new RestfulResponse<String>("00000", "新增成功", null);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		RestfulResponse<String> response = new RestfulResponse<String>("00012", "新增失敗", null);
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
