package com.lyy.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class F_assess implements Serializable{
	private String username;
	private String userimg;
	private int F_id;
	private int U_id;
	private String content;
	private String time;
	
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
	public int getF_id() {
		return F_id;
	}
	public void setF_id(int f_id) {
		F_id = f_id;
	}
	public int getU_id() {
		return U_id;
	}
	public void setU_id(int u_id) {
		U_id = u_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public F_assess(String username, String userimg, int f_id, int u_id,
			String content, String time) {
		super();
		this.username = username;
		this.userimg = userimg;
		F_id = f_id;
		U_id = u_id;
		this.content = content;
		this.time = time;
	}
	
}
