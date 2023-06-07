package com.sstixbackend.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sstixbackend.model.Events;
import com.sstixbackend.model.Orders;

@SpringBootTest
public class OrdersRepositoryTest {

	@Autowired
	private OrdersRepository or;

//	@Test
	void testSaveorder() {
		Orders order = new Orders();
		order.setUsersId(2);
		order.setQuantity(3);
		order.setEventPrice(100);
		order.setStatus(1);
		
		Events event = Events.builder().id(2).build();
		order.setEvents(event);
		
		or.save(order);
	}
	
	@Test
	void selectOrder() {
		Optional<Orders> optional = or.findById(1);
		if (optional.isPresent()) {
			Orders order = optional.get();
			System.out.println(order);
		}
		
		List<Orders> orders = or.findAll();
		System.out.println(orders);
		
	}
}
