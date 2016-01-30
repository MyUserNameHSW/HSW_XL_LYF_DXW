package com.lyy.xl.bean;

import java.io.Serializable;

public class StoreBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int S_id;
	private String address;
	private String name;
	private String depict;
	private int level;
	private String img;
	private String phone;
	private double lal1;
	private double lal2;
	public StoreBean() {
		super();
	}
	public StoreBean(int s_id, String address, String name, String depict,
			int level, String img, String phone, double lal1, double lal2) {
		super();
		S_id = s_id;
		this.address = address;
		this.name = name;
		this.depict = depict;
		this.level = level;
		this.img = img;
		this.phone = phone;
		this.lal1 = lal1;
		this.lal2 = lal2;
	}
	public int getS_id() {
		return S_id;
	}
	public void setS_id(int s_id) {
		S_id = s_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getLal1() {
		return lal1;
	}
	public void setLal1(double lal1) {
		this.lal1 = lal1;
	}
	public double getLal2() {
		return lal2;
	}
	public void setLal2(double lal2) {
		this.lal2 = lal2;
	}
	@Override
	public String toString() {
		return "StoreBean [S_id=" + S_id + ", address=" + address + ", name="
				+ name + ", depict=" + depict + ", level=" + level + ", img="
				+ img + ", phone=" + phone + ", lal1=" + lal1 + ", lal2="
				+ lal2 + "]";
	}
	
}
