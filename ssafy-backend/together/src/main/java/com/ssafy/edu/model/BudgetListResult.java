package com.ssafy.edu.model;

public class BudgetListResult {
	private String pro_id;
	private String pro_name;
	private String quantity;
	private String price;

	public BudgetListResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BudgetListResult(String pro_id, String pro_name, String quantity, String price) {
		super();
		this.pro_id = pro_id;
		this.pro_name = pro_name;
		this.quantity = quantity;
		this.price = price;
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
		return "BudgetListResult [pro_id=" + pro_id + ", pro_name=" + pro_name + ", quantity=" + quantity + ", price="
				+ price + "]";
	}

}
