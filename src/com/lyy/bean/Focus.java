package com.lyy.bean;
//关注表
public class Focus { 
	@SuppressWarnings("unused")
	private int FC_id;
	private int FOCU_id;
	private int U_id;
	public int getFOCU_id() {
		return FOCU_id;
	}
	public void setFOCU_id(int fOCU_id) {
		FOCU_id = fOCU_id;
	}
	public int getU_id() {
		return U_id;
	}
	public void setU_id(int u_id) {
		U_id = u_id;
	}
	public Focus(int fOCU_id, int u_id) {
		super();
		FOCU_id = fOCU_id;
		U_id = u_id;
	}
	
	

}
