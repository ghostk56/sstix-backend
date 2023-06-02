package com.sstixbackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sstixbackend.model.Orders;

@SpringBootTest
public class OrdersRepositoryTest {

	@Autowired
	private OrdersRepository or;

	@Test
	void testSaveorder() {
		Orders order = new Orders();
		order.setUsersId(1);
		order.setEventsId(2);
		order.setQuantity(3);
		order.setEventPrice(100);
		order.setStatus(1);
		
		or.save(order);
	}
}
