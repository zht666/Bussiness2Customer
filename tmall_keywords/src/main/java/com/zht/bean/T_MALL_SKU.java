package com.zht.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
//sku库存表
public class T_MALL_SKU {

	//java属性和solr所存储的文本数据的字段之间有一个对应的关系，需要通过@Field注解提示solr，把java对象的属性和solr字段之间的对应关系关联起来
	//用@Field配置的好处1：不一定每个字段都在solr中用到，2：解除了solr和java的耦合性
	@Field("id")
	private int id;
	@Field("shp_id")
	private int shp_id;//商品id
	@Field("kc")
	private int kc;//库存
	@Field("jg")
	private double jg;//价格 solr不支持BigDecimal应用类型，需要转化
	@Field("chjshj")
	private Date chjshj;//创建时间
	@Field("sku_mch")
	private String sku_mch;//sku名称
	@Field("kcdz")
	private String kcdz;//库存地址
	@Field("sku_xl")
	private long sku_xl;//sku销量 

	public long getSku_xl() {
		return sku_xl;
	}

	public void setSku_xl(long sku_xl) {
		this.sku_xl = sku_xl;
	}

	public String getKcdz() {
		return kcdz;
	}

	public void setKcdz(String kcdz) {
		this.kcdz = kcdz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShp_id() {
		return shp_id;
	}

	public void setShp_id(int shp_id) {
		this.shp_id = shp_id;
	}

	public int getKc() {
		return kc;
	}

	public void setKc(int kc) {
		this.kc = kc;
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

	public double getJg() {
		return jg;
	}

	public void setJg(double jg) {
		this.jg = jg;
	}


}
