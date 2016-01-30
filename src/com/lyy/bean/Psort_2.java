package com.lyy.bean;

import java.io.Serializable;

public class Psort_2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ps1_id;
	private int ps2_id;
	private String ps2_name;

	public Psort_2(int ps1_id, int ps2_id, String ps2_name) {
		super();
		this.ps1_id = ps1_id;
		this.ps2_id = ps2_id;
		this.ps2_name = ps2_name;
	}

	public int getPs1_id() {
		return ps1_id;
	}

	public void setPs1_id(int ps1_id) {
		this.ps1_id = ps1_id;
	}

	public int getPs2_id() {
		return ps2_id;
	}

	public void setPs2_id(int ps2_id) {
		this.ps2_id = ps2_id;
	}

	public String getPs2_name() {
		return ps2_name;
	}

	public void setPs2_name(String ps2_name) {
		this.ps2_name = ps2_name;
	}

	@Override
	public String toString() {
		return "Psort_2 [ps1_id=" + ps1_id + ", ps2_id=" + ps2_id
				+ ", ps2_name=" + ps2_name + "]";
	}

}
