package com.jwt.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.user.models.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
	
	RefreshToken findByToken(String token);
	
}
