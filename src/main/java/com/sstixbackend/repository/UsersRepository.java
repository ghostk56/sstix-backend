package com.sstixbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sstixbackend.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	Users findByUserName(String userName);
	
}
