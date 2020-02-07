package com.ssafy.edu.model;

public class ReviewResult {

	private String user_email;
	private String budget_title;
	private String user_name;
	private String img;
	private String content;
	private Budget budget;

	public ReviewResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewResult(String user_email, String budget_title, String user_name, String img, String content,
			Budget budget) {
		super();
		this.user_email = user_email;
		this.budget_title = budget_title;
		this.user_name = user_name;
		this.img = img;
		this.content = content;
		this.budget = budget;
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	@Override
	public String toString() {
		return "ReviewResult [user_email=" + user_email + ", budget_title=" + budget_title + ", user_name=" + user_name
				+ ", img=" + img + ", content=" + content + ", budget=" + budget + "]";
	}

}
