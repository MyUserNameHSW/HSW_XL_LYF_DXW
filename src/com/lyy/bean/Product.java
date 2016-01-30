package com.lyy.bean;

import java.io.Serializable;

public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int p_id;
	private String name;
	private String store;
	private String depict;
	private String type;
	private double price;
	private String img;
	private int buy_num;
	private int col_num;
	private String phone;
	private String type2;
    
	public Product() {
		super();
	}
	
	public Product(String phone) {
		super();
		this.phone = phone;
	}


	public Product(int p_id, String name, String img,int price,int buy_num) {
		super();
		this.p_id = p_id;
		this.name = name;
		this.img = img;
		this.price = price;
		this.buy_num = buy_num;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getBuy_num() {
		return buy_num;
	}
	public void setBuy_num(int buy_num) {
		this.buy_num = buy_num;
	}
	public int getCol_num() {
		return col_num;
	}
	public void setCol_num(int col_num) {
		this.col_num = col_num;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	@Override
	public String toString() {
		return "Goods [name=" + name + ", store=" + store + ", depict=" + depict + ", type=" + type + ", price=" + price
				+ ", img=" + img + ", buy_num=" + buy_num + ", col_num=" + col_num + ", phone=" + phone + ", type2="
				+ type2 + "]";
	}
}
