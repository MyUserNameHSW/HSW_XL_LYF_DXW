package com.lyy.xl.bean;

import java.io.Serializable;

public class ReceiveMsgBean implements Serializable{
  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String phone;
	private String address;
	
	public ReceiveMsgBean() {
		super();
	}

	public ReceiveMsgBean(String name, String phone, String address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
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

	@Override
	public String toString() {
		return "ReceiveMsgBean [name=" + name + ", phone=" + phone
				+ ", address=" + address + "]";
	}
	
	
}
