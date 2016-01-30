package com.lyy.bean;

import java.io.Serializable;

public class Essay_Collections implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int e_id;
	private String name;
	private String aricl;
	private String img;
	private String simg;
	private int ce_id;
	private String k_time;

	public Essay_Collections(int e_id, String name, String aricl, String img,
			String simg, int ce_id, String k_time) {
		super();
		this.e_id = e_id;
		this.name = name;
		this.aricl = aricl;
		this.img = img;
		this.simg = simg;
		this.ce_id = ce_id;
		this.k_time = k_time;
	}

	public int getE_id() {
		return e_id;
	}

	public void setE_id(int e_id) {
		this.e_id = e_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAricl() {
		return aricl;
	}

	public void setAricl(String aricl) {
		this.aricl = aricl;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSimg() {
		return simg;
	}

	public void setSimg(String simg) {
		this.simg = simg;
	}

	public int getCe_id() {
		return ce_id;
	}

	public void setCe_id(int ce_id) {
		this.ce_id = ce_id;
	}

	public String getK_time() {
		return k_time;
	}

	public void setK_time(String k_time) {
		this.k_time = k_time;
	}

	@Override
	public String toString() {
		return "Essay_Collections [e_id=" + e_id + ", name=" + name
				+ ", aricl=" + aricl + ", img=" + img + ", simg=" + simg
				+ ", ce_id=" + ce_id + ", k_time=" + k_time + "]";
	}

}
