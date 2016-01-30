package com.lyy.xl.bean;

import java.io.Serializable;

public class AssessBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int a_id;
	private int p_id;
	private int u_id;
	private int level;
	private String time;
	private String text;
	private String img;
	public AssessBean() {
		super();
	}
	public AssessBean(int a_id, int p_id, int u_id, int level, String time,
			String text, String img) {
		super();
		this.a_id = a_id;
		this.p_id = p_id;
		this.u_id = u_id;
		this.level = level;
		this.time = time;
		this.text = text;
		this.img = img;
	}
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "AssessBean [a_id=" + a_id + ", p_id=" + p_id + ", u_id=" + u_id
				+ ", level=" + level + ", time=" + time + ", text=" + text
				+ ", img=" + img + "]";
	}
	
	
	
}
