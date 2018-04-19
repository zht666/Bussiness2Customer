package com.zht.bean;

import java.util.Date;
//sku属性和属性值关联表
public class T_MALL_SKU_ATTR_VALUE {
	private int id;
	private int shxm_id;//属性名id
	private int shxzh_id;//属性值id
	private int shp_id;//商品id
	private String is_sku;//是否sku
	private Date chjshj;//创建时间
	private int sku_id;//skuid

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShxm_id() {
		return shxm_id;
	}

	public void setShxm_id(int shxm_id) {
		this.shxm_id = shxm_id;
	}

	public int getShxzh_id() {
		return shxzh_id;
	}

	public void setShxzh_id(int shxzh_id) {
		this.shxzh_id = shxzh_id;
	}

	public int getShp_id() {
		return shp_id;
	}

	public void setShp_id(int shp_id) {
		this.shp_id = shp_id;
	}

	public String getIs_sku() {
		return is_sku;
	}

	public void setIs_sku(String is_sku) {
		this.is_sku = is_sku;
	}

	public Date getChjshj() {
		return chjshj;
	}

	public void setChjshj(Date chjshj) {
		this.chjshj = chjshj;
	}

	public int getSku_id() {
		return sku_id;
	}

	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}

}
