package com.lyy.bean;

import java.io.Serializable;

public class Shop_Collections implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pc_id;
	private int s_id;
	private int u_id;
	private String p_time;
	private String name;
	private String img;

	public Shop_Collections(int pc_id, int s_id, int u_id, String p_time,String name,String img) {
		super();
		this.pc_id = pc_id;
		this.s_id = s_id;
		this.u_id = u_id;
		this.p_time = p_time;
		this.name = name;
		this.img = img;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPc_id() {
		return pc_id;
	}

	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
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

	@Override
	public String toString() {
		return "Shop_Collections [pc_id=" + pc_id + ", s_id=" + s_id
				+ ", u_id=" + u_id + ", p_time=" + p_time + ", name=" + name
				+ ", img=" + img + "]";
	}
}
