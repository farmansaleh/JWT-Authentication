package com.jwt.user.models;

public class AuthResponse {
	
	private String token;
	private String refreshToken;

	public String getToken() {
		return token;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public AuthResponse(String token,String refreshToken) {
		this.token=token;
		this.refreshToken=refreshToken;
	}

}
