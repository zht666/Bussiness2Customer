package com.zht.bean;

import org.apache.solr.client.solrj.beans.Field;

//solr是文本型数据库，在存储的时候里面是没java的引用类型的，在solr中存储的是xml格式或者json格式，在这种格式下是没有java引用类型的
public class KEYWORDS_T_MALL_SKU extends T_MALL_SKU {

	@Field("shp_tp")
	private String shp_tp;

	public String getShp_tp() {
		return shp_tp;
	}

	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}
	
	

}
