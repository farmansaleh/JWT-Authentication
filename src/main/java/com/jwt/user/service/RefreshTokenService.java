package com.jwt.user.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.user.models.RefreshToken;
import com.jwt.user.repositories.RefreshTokenRepo;
import com.jwt.user.repositories.UserRepo;

@Service
public class RefreshTokenService {
	
	@Autowired
	RefreshTokenRepo refreshTokenRepo;
	@Autowired
	UserRepo userRepo;
	
	public Optional<RefreshToken> findByToken(String token){
		return Optional.of(refreshTokenRepo.findByToken(token));
	}
	
	public RefreshToken createRefreshToken(Integer userId) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setUser(userRepo.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(1000*60*60));
		return refreshTokenRepo.save(refreshToken);
	}
	
	public RefreshToken verifyExpiration(RefreshToken refreshToken) {
		if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(refreshToken);
			throw new RuntimeException("Refresh token have expired..!");
		}
		return refreshToken;
	}
	
}
