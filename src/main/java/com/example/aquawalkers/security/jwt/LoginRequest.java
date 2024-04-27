package com.example.aquawalkers.security.jwt;

public class LoginRequest {

	private String name;
	private String password;

	public LoginRequest() {
	}

	public LoginRequest(String username, String password) {
		this.name = username;
		this.password = password;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginRequest [username=" + name + ", password=" + password + "]";
	}
}
