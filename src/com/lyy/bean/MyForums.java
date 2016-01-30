package com.lyy.bean;

import java.io.Serializable;

public class MyForums implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int f_id;
	private int u_id;
	private String f_time;
	private String articl;
	private String theme;
	private String username;
	private String userimg;

	public MyForums(int f_id, int u_id, String f_time, String articl,
			String theme, String username, String userimg) {
		super();
		this.f_id = f_id;
		this.u_id = u_id;
		this.f_time = f_time;
		this.articl = articl;
		this.theme = theme;
		this.username = username;
		this.userimg = userimg;
	}

	public int getF_id() {
		return f_id;
	}

	public void setF_id(int f_id) {
		this.f_id = f_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getF_time() {
		return f_time;
	}

	public void setF_time(String f_time) {
		this.f_time = f_time;
	}

	public String getArticl() {
		return articl;
	}

	public void setArticl(String articl) {
		this.articl = articl;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserimg() {
		return userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	@Override
	public String toString() {
		return "MyForums [f_id=" + f_id + ", u_id=" + u_id + ", f_time="
				+ f_time + ", articl=" + articl + ", theme=" + theme
				+ ", username=" + username + ", userimg=" + userimg + "]";
	}

}
