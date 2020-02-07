package com.ssafy.edu.model;

import java.io.Serializable;

public class Review implements Serializable {

	private String user_email;
	private String budget_title;
	private String img;
	private String content;

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Review(String user_email, String budget_title, String img, String content) {
		super();
		this.user_email = user_email;
		this.budget_title = budget_title;
		this.img = img;
		this.content = content;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getBudget_title() {
		return budget_title;
	}

	public void setBudget_title(String budget_title) {
		this.budget_title = budget_title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Review [user_email=" + user_email + ", budget_title=" + budget_title + ", img=" + img + ", content="
				+ content + "]";
	}

}
