package com.zht.bean;
//用户地址表
public class T_MALL_ADDRESS {

	private int id;
	private String yh_dz;//用户地址
	private String dzzt;//地址状态
	private int yh_id;//用户id
	private String shjr;//收件人
	private String lxfsh;//联系方式

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYh_dz() {
		return yh_dz;
	}

	public void setYh_dz(String yh_dz) {
		this.yh_dz = yh_dz;
	}

	public String getDzzt() {
		return dzzt;
	}

	public void setDzzt(String dzzt) {
		this.dzzt = dzzt;
	}

	public int getYh_id() {
		return yh_id;
	}

	public void setYh_id(int yh_id) {
		this.yh_id = yh_id;
	}

	public String getShjr() {
		return shjr;
	}

	public void setShjr(String shjr) {
		this.shjr = shjr;
	}

	public String getLxfsh() {
		return lxfsh;
	}

	public void setLxfsh(String lxfsh) {
		this.lxfsh = lxfsh;
	}

}
