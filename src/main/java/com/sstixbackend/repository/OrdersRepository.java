package com.sstixbackend.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sstixbackend.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	@EntityGraph(attributePaths = "events")
	List<Orders> findByUsersId(Integer userId, Sort sort);

	@EntityGraph(attributePaths = "events")
	List<Orders> findAll(Sort sort);

}
