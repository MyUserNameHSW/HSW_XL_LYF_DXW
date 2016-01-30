package com.lyy.bean;

import java.io.Serializable;

public class S_provide implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int sp_id;
	private int s_id;
	private String name;
	private int sp_num;
	private String sctipt;
	private String img;
	private String logo;
	private String shop_name;
	private String shop_address;

	public S_provide(int sp_id, int s_id, String name, int sp_num,
			String sctipt, String img, String logo, String shop_name,String shop_address) {
		super();
		this.sp_id = sp_id;
		this.s_id = s_id;
		this.name = name;
		this.sp_num = sp_num;
		this.sctipt = sctipt;
		this.img = img;
		this.logo = logo;
		this.shop_name = shop_name;
		this.shop_address = shop_address;
	}

	public String getShop_address() {
		return shop_address;
	}

	public void setShop_address(String shop_address) {
		this.shop_address = shop_address;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public int getSp_id() {
		return sp_id;
	}

	public void setSp_id(int sp_id) {
		this.sp_id = sp_id;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSp_num() {
		return sp_num;
	}

	public void setSp_num(int sp_num) {
		this.sp_num = sp_num;
	}

	public String getSctipt() {
		return sctipt;
	}

	public void setSctipt(String sctipt) {
		this.sctipt = sctipt;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "S_provide [sp_id=" + sp_id + ", s_id=" + s_id + ", name="
				+ name + ", sp_num=" + sp_num + ", sctipt=" + sctipt + ", img="
				+ img + ", logo=" + logo + ", shop_name=" + shop_name
				+ ", shop_address=" + shop_address + "]";
	}
}
