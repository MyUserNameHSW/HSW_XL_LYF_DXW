package com.lyy.bean;

import java.io.Serializable;

public class Esort1Bean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int Es1_id;
	private String es1_name;
	private int type;
	public Esort1Bean() {
		super();
	}
	public Esort1Bean(int es1_id, String es1_name, int type) {
		super();
		Es1_id = es1_id;
		this.es1_name = es1_name;
		this.type = type;
	}
	public int getEs1_id() {
		return Es1_id;
	}
	public void setEs1_id(int es1_id) {
		Es1_id = es1_id;
	}
	public String getEs1_name() {
		return es1_name;
	}
	public void setEs1_name(String es1_name) {
		this.es1_name = es1_name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Esort1Bean [Es1_id=" + Es1_id + ", es1_name=" + es1_name
				+ ", type=" + type + "]";
	}
	
}
