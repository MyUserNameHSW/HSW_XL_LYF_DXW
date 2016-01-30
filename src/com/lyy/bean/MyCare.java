package com.lyy.bean;

import java.io.Serializable;

public class MyCare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pl_id;
	private String name;
	private int pp_id;
	private int u_id;
	private String over_time;
	private String img;
	private String username;
	private String phoneNum;

	public MyCare(int pl_id, String name, int pp_id, int u_id, String over_time, String img,String username,String phoneNum) {
		super();
		this.pl_id = pl_id;
		this.name = name;
		this.pp_id = pp_id;
		this.u_id = u_id;
		this.over_time = over_time;
		this.img = img;
		this.username = username;
		this.phoneNum = phoneNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getPl_id() {
		return pl_id;
	}

	public void setPl_id(int pl_id) {
		this.pl_id = pl_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPp_id() {
		return pp_id;
	}

	public void setPp_id(int pp_id) {
		this.pp_id = pp_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getOver_time() {
		return over_time;
	}

	public void setOver_time(String over_time) {
		this.over_time = over_time;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "MyCare [pl_id=" + pl_id + ", name=" + name + ", pp_id=" + pp_id
				+ ", u_id=" + u_id + ", over_time=" + over_time + ", img="
				+ img + ", username=" + username + ", phoneNum=" + phoneNum
				+ "]";
	}

}
