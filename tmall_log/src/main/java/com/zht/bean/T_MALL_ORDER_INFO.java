package com.zht.bean;

import java.util.Date;
//订单信息表
public class T_MALL_ORDER_INFO {

	private int id;
	private int dd_id;//订单id
	private int sku_id;//skuid
	private Date chjshj;//创建时间
	private String sku_mch;//sku名称
	private String shp_tp;//商品图片
	private double sku_jg;//sku价格
	private int sku_shl;//sku数量
	private String sku_kcdz;//sku库存地址
	private int gwch_id;//购物车id
	private int flow_id;//物流id

	public int getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(int flow_id) {
		this.flow_id = flow_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDd_id() {
		return dd_id;
	}

	public void setDd_id(int dd_id) {
		this.dd_id = dd_id;
	}

	public int getSku_id() {
		return sku_id;
	}

	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}

	public Date getChjshj() {
		return chjshj;
	}

	public void setChjshj(Date chjshj) {
		this.chjshj = chjshj;
	}

	public String getSku_mch() {
		return sku_mch;
	}

	public void setSku_mch(String sku_mch) {
		this.sku_mch = sku_mch;
	}

	public String getShp_tp() {
		return shp_tp;
	}

	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}

	public double getSku_jg() {
		return sku_jg;
	}

	public void setSku_jg(double sku_jg) {
		this.sku_jg = sku_jg;
	}

	public int getSku_shl() {
		return sku_shl;
	}

	public void setSku_shl(int sku_shl) {
		this.sku_shl = sku_shl;
	}

	public String getSku_kcdz() {
		return sku_kcdz;
	}

	public void setSku_kcdz(String sku_kcdz) {
		this.sku_kcdz = sku_kcdz;
	}

	public int getGwch_id() {
		return gwch_id;
	}

	public void setGwch_id(int gwch_id) {
		this.gwch_id = gwch_id;
	}

}
