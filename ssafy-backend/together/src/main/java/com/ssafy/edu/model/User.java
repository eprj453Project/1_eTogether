package com.ssafy.edu.model;

import java.io.Serializable;

public class User implements Serializable {
	private int count;
	private String email;
	private String pwd;
	private String temp_pwd;
	private String name;
	private int withdraw; // 0 이면 회원 // 1이면 탈퇴한 회원
	private int auth; // 3이면 관리자 // 1이면 사용자
	private String phone;

	public User() {
		super();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public String getTemp_pwd() {
		return temp_pwd;
	}

	public void setTemp_pwd(String temp_pwd) {
		this.temp_pwd = temp_pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String storePhone(String phone) {
		String phonenumber = "";
		return phonenumber;
	}

	public User(int count, String email, String pwd, String temp_pwd, String name, int withdraw, int auth,
			String phone) {
		super();
		this.count = count;
		this.email = email;
		this.pwd = pwd;
		this.temp_pwd = temp_pwd;
		this.name = name;
		this.withdraw = withdraw;
		this.auth = auth;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [count=" + count + ", email=" + email + ", pwd=" + pwd + ", temp_pwd=" + temp_pwd + ", name="
				+ name + ", withdraw=" + withdraw + ", auth=" + auth + ", phone=" + phone + "]";
	}

}
