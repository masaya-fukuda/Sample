package com.kintai.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kintai.business.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUserId(String userId); 
}
