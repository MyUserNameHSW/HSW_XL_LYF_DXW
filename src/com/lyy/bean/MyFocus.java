package com.lyy.bean;

import java.io.Serializable;

public class MyFocus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int fc_id;
	private int focu_id;
	private int u_id;
    private String username;
	public MyFocus(int fc_id, int focu_id, int u_id,String username) {
		super();
		this.fc_id = fc_id;
		this.focu_id = focu_id;
		this.u_id = u_id;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getFc_id() {
		return fc_id;
	}

	public void setFc_id(int fc_id) {
		this.fc_id = fc_id;
	}

	public int getFocu_id() {
		return focu_id;
	}

	public void setFocu_id(int focu_id) {
		this.focu_id = focu_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	@Override
	public String toString() {
		return "MyFocus [fc_id=" + fc_id + ", focu_id=" + focu_id + ", u_id="
				+ u_id + ", username=" + username + "]";
	}
}
