package com.lyy.bean;

import java.io.Serializable;

public class PC_assess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pa_id;
	private int pp_id;
	private int u_id;
	private String time;
	private String text;
    private String u_name;
	public PC_assess(int pa_id, int pp_id, int u_id, String time, String text,String u_name) {
		super();
		this.pa_id = pa_id;
		this.pp_id = pp_id;
		this.u_id = u_id;
		this.time = time;
		this.text = text;
		this.u_name = u_name;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public int getPa_id() {
		return pa_id;
	}

	public void setPa_id(int pa_id) {
		this.pa_id = pa_id;
	}

	public int getPp_id() {
		return pp_id;
	}

	public void setPp_id(int pp_id) {
		this.pp_id = pp_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
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

	@Override
	public String toString() {
		return "PC_assess [pa_id=" + pa_id + ", pp_id=" + pp_id + ", u_id="
				+ u_id + ", time=" + time + ", text=" + text + ", u_name="
				+ u_name + "]";
	}

}
