package com.lyy.bean;

import java.io.Serializable;

public class MyForms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int fr_id;
	private int p_id;
	private int u_id;
	private int state;// 1、未付款 2、已付款 3、待评价
	private int r_id;
	private double price;
	private int account;
	private String subTime;
	private String p_name;
	String u_name;
	String orderNumber;
	String img;

	public MyForms(int fr_id, int p_id, int u_id, int state, int r_id,
			double price, int account, String subTime,String p_name,String u_name,String orderNumber,String img) {
		super();
		this.fr_id = fr_id;
		this.p_id = p_id;
		this.u_id = u_id;
		this.state = state;
		this.r_id = r_id;
		this.price = price;
		this.account = account;
		this.subTime = subTime;
		this.p_name = p_name;
		this.u_name = u_name;
		this.orderNumber = orderNumber;
		this.img = img;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public int getFr_id() {
		return fr_id;
	}

	public void setFr_id(int fr_id) {
		this.fr_id = fr_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getR_id() {
		return r_id;
	}

	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}

	@Override
	public String toString() {
		return "MyForms [fr_id=" + fr_id + ", p_id=" + p_id + ", u_id=" + u_id
				+ ", state=" + state + ", r_id=" + r_id + ", price=" + price
				+ ", account=" + account + ", subTime=" + subTime + ", p_name="
				+ p_name + ", u_name=" + u_name + ", orderNumber="
				+ orderNumber + ", img=" + img + "]";
	}

}
