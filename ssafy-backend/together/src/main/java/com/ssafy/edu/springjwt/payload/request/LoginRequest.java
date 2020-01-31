package com.ssafy.edu.springjwt.payload.request;

public class LoginRequest {
	
	private String email;
	
	private String pwd;
	
	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoginRequest( String email,  String pwd) {
		super();
		this.email = email;
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}