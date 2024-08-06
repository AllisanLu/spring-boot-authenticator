package com.example.authenticator.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class Token {
	
	String token;

	public Token(String token) {
		super();
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
}
