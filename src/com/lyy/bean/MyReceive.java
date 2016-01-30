package com.lyy.bean;

import java.io.Serializable;

public class MyReceive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int r_id;
	private int u_id;
	private String name;
	private String phone;
	private String address;
	private String username;

	public MyReceive(int r_id, int u_id, String name, String phone,
			String address, String username) {
		super();
		this.r_id = r_id;
		this.u_id = u_id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.username = username;
	}

	public int getR_id() {
		return r_id;
	}

	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "MyReceive [r_id=" + r_id + ", u_id=" + u_id + ", name=" + name
				+ ", phone=" + phone + ", address=" + address + ", username="
				+ username + "]";
	}
}
