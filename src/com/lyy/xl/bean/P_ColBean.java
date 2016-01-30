package com.lyy.xl.bean;

import java.io.Serializable;

public class P_ColBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int PC_id;
	private int P_id;
	private int U_id;
	private String p_time;
	public P_ColBean() {
		super();
	}
	public P_ColBean(int pC_id, int p_id, int u_id, String p_time) {
		super();
		PC_id = pC_id;
		P_id = p_id;
		U_id = u_id;
		this.p_time = p_time;
	}
	public int getPC_id() {
		return PC_id;
	}
	public void setPC_id(int pC_id) {
		PC_id = pC_id;
	}
	public int getP_id() {
		return P_id;
	}
	public void setP_id(int p_id) {
		P_id = p_id;
	}
	public int getU_id() {
		return U_id;
	}
	public void setU_id(int u_id) {
		U_id = u_id;
	}
	public String getP_time() {
		return p_time;
	}
	public void setP_time(String p_time) {
		this.p_time = p_time;
	}
	@Override
	public String toString() {
		return "P_ColBean [PC_id=" + PC_id + ", P_id=" + P_id + ", U_id="
				+ U_id + ", p_time=" + p_time + "]";
	}
	
}
