package com.lyy.bean;

import java.io.Serializable;

public class CartBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int c_id;
	private int u_id;
	private int p_id;
	private double price;
	private int account;
	private String time;
	private String pname;
	private String pic;
	private boolean isChoosed;

	public CartBean(int c_id, int u_id, int p_id, double price, int account, String time, String pname, String pic,
			boolean isChoosed) {
		super();
		this.c_id = c_id;
		this.u_id = u_id;
		this.p_id = p_id;
		this.price = price;
		this.account = account;
		this.time = time;
		this.pname = pname;
		this.pic = pic;
		this.isChoosed = isChoosed;
	}

	public boolean isChoosed() {
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed) {
		this.isChoosed = isChoosed;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Override
	public String toString() {
		return "CartBean [c_id=" + c_id + ", u_id=" + u_id + ", p_id=" + p_id
				+ ", price=" + price + ", account=" + account + ", time="
				+ time + ", pname=" + pname + ", pic=" + pic + ", isChoosed="
				+ isChoosed + "]";
	}
}
