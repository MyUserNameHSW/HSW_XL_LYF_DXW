package com.lyy.bean;

import java.io.Serializable;

public class Collections implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pc_id;
	private int p_id;
	private int u_id;
	private String p_time;
	String p_name;
	double p_price;
	String img;

	public Collections(int pc_id, int p_id, int u_id, String p_time,
			String p_name, double p_price,String img) {
		super();
		this.pc_id = pc_id;
		this.p_id = p_id;
		this.u_id = u_id;
		this.p_time = p_time;
		this.p_name = p_name;
		this.p_price = p_price;
		this.img = img;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getPc_id() {
		return pc_id;
	}

	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getP_time() {
		return p_time;
	}

	public void setP_time(String p_time) {
		this.p_time = p_time;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public double getP_price() {
		return p_price;
	}

	public void setP_price(double p_price) {
		this.p_price = p_price;
	}

	@Override
	public String toString() {
		return "Collections [pc_id=" + pc_id + ", p_id=" + p_id + ", u_id="
				+ u_id + ", p_time=" + p_time + ", p_name=" + p_name
				+ ", p_price=" + p_price + ", img=" + img + "]";
	}

}
