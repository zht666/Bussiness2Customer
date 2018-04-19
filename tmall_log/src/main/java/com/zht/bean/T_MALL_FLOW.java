package com.zht.bean;

import java.util.Date;
//物流表
public class T_MALL_FLOW {
	private int id;
	private String psfsh;//配送方式
	private Date psshj;//配送时间
	private String psmsh;//配送描述
	private int yh_id;//用户id
	private int dd_id;//订单id
	private String mqdd;//目前地点
	private String mdd;//目的地
	private String ywy;//业务员
	private String lxfsh;//联系方式

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPsfsh() {
		return psfsh;
	}

	public void setPsfsh(String psfsh) {
		this.psfsh = psfsh;
	}

	public Date getPsshj() {
		return psshj;
	}

	public void setPsshj(Date psshj) {
		this.psshj = psshj;
	}

	public String getPsmsh() {
		return psmsh;
	}

	public void setPsmsh(String psmsh) {
		this.psmsh = psmsh;
	}

	public int getYh_id() {
		return yh_id;
	}

	public void setYh_id(int yh_id) {
		this.yh_id = yh_id;
	}

	public int getDd_id() {
		return dd_id;
	}

	public void setDd_id(int dd_id) {
		this.dd_id = dd_id;
	}

	public String getMqdd() {
		return mqdd;
	}

	public void setMqdd(String mqdd) {
		this.mqdd = mqdd;
	}

	public String getMdd() {
		return mdd;
	}

	public void setMdd(String mdd) {
		this.mdd = mdd;
	}

	public String getYwy() {
		return ywy;
	}

	public void setYwy(String ywy) {
		this.ywy = ywy;
	}

	public String getLxfsh() {
		return lxfsh;
	}

	public void setLxfsh(String lxfsh) {
		this.lxfsh = lxfsh;
	}

}
