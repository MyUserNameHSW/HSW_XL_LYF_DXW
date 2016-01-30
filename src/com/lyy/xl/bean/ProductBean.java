package com.lyy.xl.bean;

import java.io.Serializable;

public class ProductBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int p_id;
	private String name;
	private int s_id;
	private String depict;
	private String type;
	private double price;
	private String img;
	private int buy_num;
	private int col_num;
	private String phone;
	private int ps2_id;
	private double postage;
	public ProductBean() {
		super();
	}
	public ProductBean(int p_id, String name, int s_id, String depict,
			String type, double price, String img, int buy_num, int col_num,
			String phone, int ps2_id, double postage) {
		super();
		this.p_id = p_id;
		this.name = name;
		this.s_id = s_id;
		this.depict = depict;
		this.type = type;
		this.price = price;
		this.img = img;
		this.buy_num = buy_num;
		this.col_num = col_num;
		this.phone = phone;
		this.ps2_id = ps2_id;
		this.postage = postage;
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
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
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
	public int getPs2_id() {
		return ps2_id;
	}
	public void setPs2_id(int ps2_id) {
		this.ps2_id = ps2_id;
	}
	public double getPostage() {
		return postage;
	}
	public void setPostage(double postage) {
		this.postage = postage;
	}
	@Override
	public String toString() {
		return "ProductBean [p_id=" + p_id + ", name=" + name + ", s_id="
				+ s_id + ", depict=" + depict + ", type=" + type + ", price="
				+ price + ", img=" + img + ", buy_num=" + buy_num
				+ ", col_num=" + col_num + ", phone=" + phone + ", ps2_id="
				+ ps2_id + ", postage=" + postage + "]";
	}
	
	
	
}
