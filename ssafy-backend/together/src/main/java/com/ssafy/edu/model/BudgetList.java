package com.ssafy.edu.model;

import java.util.Date;

//예산안 상세보기에 필요한 정보들
public class BudgetList {

	private String user_email; // db에 저장할때만 넣어주면 된다.
	private String budget_title; // db에 저장할때만 넣어주면 된다.
	private String pro_id;
	private String pro_name;
	private String quantity;
	private String price;

	public BudgetList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BudgetList(String user_email, String budget_title, String pro_id, String pro_name, String quantity,
			String price) {
		super();
		this.user_email = user_email;
		this.budget_title = budget_title;
		this.pro_id = pro_id;
		this.pro_name = pro_name;
		this.quantity = quantity;
		this.price = price;
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

	public String getPro_id() {
		return pro_id;
	}

	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "BudgetList [user_email=" + user_email + ", budget_title=" + budget_title + ", pro_id=" + pro_id
				+ ", pro_name=" + pro_name + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
