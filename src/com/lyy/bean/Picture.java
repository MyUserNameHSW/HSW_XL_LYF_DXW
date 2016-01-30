package com.lyy.bean;

public class Picture {
	String P_img;
	String f_time;

	public String getP_img() {
		return P_img;
	}

	public void setP_img(String p_img) {
		P_img = p_img;
	}

	public String getF_time() {
		return f_time;
	}

	public void setF_time(String f_time) {
		this.f_time = f_time;
	}

	public Picture(String p_img) {
		super();
		P_img = p_img;
	}

	@Override
	public String toString() {
		return "Picture [P_img=" + P_img + "]";
	}

	

}
