package com.lyy.bean;

import java.io.Serializable;

public class EssayBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int E_id;
	private int Es2_id;
	private String name;
	private String artical;
	private int col_num;
	private String image;
	private String simage;
	public EssayBean() {
		super();
	}
	public EssayBean(int e_id, int es2_id, String name, String artical,
			int col_num, String image, String simage) {
		super();
		E_id = e_id;
		Es2_id = es2_id;
		this.name = name;
		this.artical = artical;
		this.col_num = col_num;
		this.image = image;
		this.simage = simage;
	}
	public int getE_id() {
		return E_id;
	}
	public void setE_id(int e_id) {
		E_id = e_id;
	}
	public int getEs2_id() {
		return Es2_id;
	}
	public void setEs2_id(int es2_id) {
		Es2_id = es2_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtical() {
		return artical;
	}
	public void setArtical(String artical) {
		this.artical = artical;
	}
	public int getCol_num() {
		return col_num;
	}
	public void setCol_num(int col_num) {
		this.col_num = col_num;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSimage() {
		return simage;
	}
	public void setSimage(String simage) {
		this.simage = simage;
	}
	@Override
	public String toString() {
		return "EssayBean [E_id=" + E_id + ", Es2_id=" + Es2_id + ", name="
				+ name + ", artical=" + artical + ", col_num=" + col_num
				+ ", image=" + image + ", simage=" + simage + "]";
	}
		
}
