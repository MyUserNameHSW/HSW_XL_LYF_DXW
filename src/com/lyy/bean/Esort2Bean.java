package com.lyy.bean;

import java.io.Serializable;

public class Esort2Bean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int Es2_id;
	private int Es1_id;
	private String es2_name;
	public Esort2Bean() {
		super();
	}
	public Esort2Bean(int es2_id, int es1_id, String es2_name) {
		super();
		Es2_id = es2_id;
		Es1_id = es1_id;
		this.es2_name = es2_name;
	}
	public int getEs2_id() {
		return Es2_id;
	}
	public void setEs2_id(int es2_id) {
		Es2_id = es2_id;
	}
	public int getEs1_id() {
		return Es1_id;
	}
	public void setEs1_id(int es1_id) {
		Es1_id = es1_id;
	}
	public String getEs2_name() {
		return es2_name;
	}
	public void setEs2_name(String es2_name) {
		this.es2_name = es2_name;
	}
	@Override
	public String toString() {
		return "Esort2Bean [Es2_id=" + Es2_id + ", Es1_id=" + Es1_id
				+ ", es2_name=" + es2_name + "]";
	}
	
}
