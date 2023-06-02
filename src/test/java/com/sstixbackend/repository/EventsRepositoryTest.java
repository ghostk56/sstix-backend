package com.sstixbackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sstixbackend.model.Events;

@SpringBootTest
public class EventsRepositoryTest {
	
	@Autowired
	private EventsRepository er;
	
	@Test
	void testSaveEvent() {
		Events event = new Events();
		event.setName("Example Event2");
		event.setDetails("This is a sample event");
		event.setLocation("Sample Location");
		event.setOrganizer("Sample Organizer");
		event.setStatus(1);
		event.setPrice(10);
		event.setQty(100);
		event.setEventDate("2023-06-01");
		event.setImage1("image1.jpg");
		event.setImage2("image2.jpg");
		
		er.save(event);
		
	}
}
