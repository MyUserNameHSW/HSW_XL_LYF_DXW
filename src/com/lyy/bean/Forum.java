package com.lyy.bean;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Forum implements Serializable {
	private String username;
	private String userimg;
	private int F_id;
	private int U_id;
	private String F_time;
	private List<Picture> img_path;
	private String articl;
	private String theme;
	private int praise;
	private String point;

	@Override
	public String toString() {
		return "Forum [username=" + username + ", userimg=" + userimg
				+ ", F_id=" + F_id + ", U_id=" + U_id + ", F_time=" + F_time
				+ ", img_path=" + img_path + ", articl=" + articl + ", theme="
				+ theme + ", praise=" + praise + "]";
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

	public String getF_time() {
		return F_time;
	}

	public void setF_time(String f_time) {
		F_time = f_time;
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

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public List<Picture> getImg() {
		return img_path;
	}

	public void setImg(List<Picture> img_path) {
		this.img_path = img_path;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public Forum(String username, String userimg, int f_id, int u_id,
			String f_time, List<Picture> img_path, String articl, String theme,
			int praise, String point) {
		super();
		this.username = username;
		this.userimg = userimg;
		F_id = f_id;
		U_id = u_id;
		F_time = f_time;
		this.img_path = img_path;
		this.articl = articl;
		this.theme = theme;
		this.praise = praise;
		this.point = point;
	}

}
