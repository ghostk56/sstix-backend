package com.sstixbackend.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.sstixbackend.model.Events;

@SpringBootTest
public class EventsRepositoryTest {

	@Autowired
	private EventsRepository er;

//	@Test
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

	@Test
	void testSelectEvents() {
		String name = ""; // 模糊搜尋的名稱
		Integer status = 1; // 狀態
		Specification<Events> spec = Specification.where(null);
		if (name != null) {
			spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + name + "%"));
		}

		if (status != null && status != 0) {
			spec = spec.and((root, query, cb) -> cb.notEqual(root.get("status"), 0));
		}

		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<Events> events = er.findAll(spec, sort);

		events.forEach(t -> System.out.println(t.getId()));
//		for (Events event : events) {
//			System.out.println(event.getId());
//		}
	}
}
