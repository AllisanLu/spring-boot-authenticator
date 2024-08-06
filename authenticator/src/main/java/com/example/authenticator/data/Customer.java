package com.example.authenticator.data;

public class Customer {
	
	long id;
	String name;
	String email;
	String password;

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
}
