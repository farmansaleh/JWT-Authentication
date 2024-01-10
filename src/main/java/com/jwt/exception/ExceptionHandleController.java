package com.jwt.exception;

import java.sql.SQLSyntaxErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entities.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
@RestController
public class ExceptionHandleController {
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse> accessDeniedException(AccessDeniedException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(401,exception.getMessage(),null),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> handleRuntimeEx(BadCredentialsException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(401,"Username and password is invalid",null),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ApiResponse> handleSignatureEx(SignatureException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(401,"Invalid JWT Token",null),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiResponse> handleExpiredJwtEx(ExpiredJwtException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(401,"JWT Token is Expired",null),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleMethodArgumentNotValidEx(MethodArgumentNotValidException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(500,"Pass Valid Request Data",null),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLSyntaxErrorException.class)
	public ResponseEntity<ApiResponse> handleSqlSyntaxErrorEx(SQLSyntaxErrorException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(500,exception.getMessage(),null),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse> handleRuntimeEx(RuntimeException exception){
		return new ResponseEntity<ApiResponse>(new ApiResponse(500,exception.getMessage(),null),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
