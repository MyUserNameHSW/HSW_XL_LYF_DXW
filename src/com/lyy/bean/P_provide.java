package com.lyy.bean;

import java.io.Serializable;

public class P_provide implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pp_id;
	private int u_id;
	private String endTime;
	private String address;
	private String phone;
	private String exprience;
	private int type;
	private int pp_num;
	private int focus_num;
	private int state;
	private String name;
	String plant_type;
	String head;

	public P_provide(int pp_id, int u_id, String endTime, String address,
			String phone, String exprience, int type, int pp_num,
			int focus_num, int state, String name, String plant_type,String head) {
		super();
		this.pp_id = pp_id;
		this.u_id = u_id;
		this.endTime = endTime;
		this.address = address;
		this.phone = phone;
		this.exprience = exprience;
		this.type = type;
		this.pp_num = pp_num;
		this.focus_num = focus_num;
		this.state = state;
		this.name = name;
		this.plant_type = plant_type;
		this.head = head;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public String getPlant_type() {
		return plant_type;
	}

	public void setPlant_type(String plant_type) {
		this.plant_type = plant_type;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExprience() {
		return exprience;
	}

	public void setExprience(String exprience) {
		this.exprience = exprience;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPp_num() {
		return pp_num;
	}

	public void setPp_num(int pp_num) {
		this.pp_num = pp_num;
	}

	public int getFocus_num() {
		return focus_num;
	}

	public void setFocus_num(int focus_num) {
		this.focus_num = focus_num;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "P_provide [pp_id=" + pp_id + ", u_id=" + u_id + ", endTime="
				+ endTime + ", address=" + address + ", phone=" + phone
				+ ", exprience=" + exprience + ", type=" + type + ", pp_num="
				+ pp_num + ", focus_num=" + focus_num + ", state=" + state
				+ ", name=" + name + ", plant_type=" + plant_type + ", head="
				+ head + "]";
	}
}
