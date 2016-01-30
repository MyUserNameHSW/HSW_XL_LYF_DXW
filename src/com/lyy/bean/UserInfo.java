package com.lyy.bean;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int u_id;
	private String username;
	private String password;
	private String phone;
	public UserInfo() {
		super();
	}

	private String img;

	public UserInfo(int u_id, String username, String password, String phone, String img) {
		super();
		this.u_id = u_id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.img = img;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}

