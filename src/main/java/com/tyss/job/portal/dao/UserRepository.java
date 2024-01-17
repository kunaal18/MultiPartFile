package com.tyss.job.portal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.job.portal.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);

}
