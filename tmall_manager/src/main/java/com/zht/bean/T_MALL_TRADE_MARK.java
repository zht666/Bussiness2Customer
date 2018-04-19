package com.zht.bean;
//商标表
public class T_MALL_TRADE_MARK {

	private int id;
	private String ppmch;//品牌名称
	private String url;//品牌图片

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPpmch() {
		return ppmch;
	}

	public void setPpmch(String ppmch) {
		this.ppmch = ppmch;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
