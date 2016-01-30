package com.lyy.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Psort_1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ps1_id;
	private String ps1_name;
	ArrayList<Psort_2> smalllist;

	public ArrayList<Psort_2> getSmalllist() {
		return smalllist;
	}

	public void setSmalllist(ArrayList<Psort_2> smalllist) {
		this.smalllist = smalllist;
	}

	public Psort_1(int ps1_id, String ps1_name) {
		super();
		this.ps1_id = ps1_id;
		this.ps1_name = ps1_name;
	}

	public int getPs1_id() {
		return ps1_id;
	}

	public void setPs1_id(int ps1_id) {
		this.ps1_id = ps1_id;
	}

	public String getPs1_name() {
		return ps1_name;
	}

	public void setPs1_name(String ps1_name) {
		this.ps1_name = ps1_name;
	}

	@Override
	public String toString() {
		return "Psort_1 [ps1_id=" + ps1_id + ", ps1_name=" + ps1_name + "]";
	}
}
