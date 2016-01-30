package com.lyy.bean;

import java.io.Serializable;

public class GoodsImgs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int p_id;
	String imgUrl;

	public GoodsImgs(int p_id, String imgUrl) {
		super();
		this.p_id = p_id;
		this.imgUrl = imgUrl;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
