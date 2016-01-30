package com.lyy.bean;

import java.io.Serializable;

public class MyCare_Share implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int share_id;
	private int pl_id;
	private String share_time;
	private String script;
	private String t_img;
	private String m_img;
	private String b_img;

	public MyCare_Share(int share_id, int pl_id, String share_time, String script, String t_img, String m_img,
			String b_img) {
		super();
		this.share_id = share_id;
		this.pl_id = pl_id;
		this.share_time = share_time;
		this.script = script;
		this.t_img = t_img;
		this.m_img = m_img;
		this.b_img = b_img;
	}

	public int getShare_id() {
		return share_id;
	}

	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}

	public int getPl_id() {
		return pl_id;
	}

	public void setPl_id(int pl_id) {
		this.pl_id = pl_id;
	}

	public String getShare_time() {
		return share_time;
	}

	public void setShare_time(String share_time) {
		this.share_time = share_time;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getT_img() {
		return t_img;
	}

	public void setT_img(String t_img) {
		this.t_img = t_img;
	}

	public String getM_img() {
		return m_img;
	}

	public void setM_img(String m_img) {
		this.m_img = m_img;
	}

	public String getB_img() {
		return b_img;
	}

	public void setB_img(String b_img) {
		this.b_img = b_img;
	}

	@Override
	public String toString() {
		return "MyCare_Share [share_id=" + share_id + ", pl_id=" + pl_id + ", share_time=" + share_time + ", script="
				+ script + ", t_img=" + t_img + ", m_img=" + m_img + ", b_img=" + b_img + "]";
	}
}
