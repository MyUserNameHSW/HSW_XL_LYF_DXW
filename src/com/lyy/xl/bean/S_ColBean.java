package com.lyy.xl.bean;

import java.io.Serializable;

public class S_ColBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SC_id;
	private int S_id;
	private int U_id;
	private String s_time;
	public S_ColBean() {
		super();
	}
	public S_ColBean(int sC_id, int s_id, int u_id, String s_time) {
		super();
		SC_id = sC_id;
		S_id = s_id;
		U_id = u_id;
		this.s_time = s_time;
	}
	public int getSC_id() {
		return SC_id;
	}
	public void setSC_id(int sC_id) {
		SC_id = sC_id;
	}
	public int getS_id() {
		return S_id;
	}
	public void setS_id(int s_id) {
		S_id = s_id;
	}
	public int getU_id() {
		return U_id;
	}
	public void setU_id(int u_id) {
		U_id = u_id;
	}
	public String getS_time() {
		return s_time;
	}
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}
	@Override
	public String toString() {
		return "S_ColBean [SC_id=" + SC_id + ", S_id=" + S_id + ", U_id="
				+ U_id + ", s_time=" + s_time + "]";
	}
	
}
