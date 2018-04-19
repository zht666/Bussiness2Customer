package com.zht.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zht.bean.KEYWORDS_T_MALL_SKU;
import com.zht.util.MyPropertiesUtil;

@Controller
public class KeywordsController {

	@RequestMapping(value = "keywords")
	@ResponseBody
	public List<KEYWORDS_T_MALL_SKU> keywords(String keywords) {
		List<KEYWORDS_T_MALL_SKU> list_sku = new ArrayList<KEYWORDS_T_MALL_SKU>();
		// 创建solr服务
		HttpSolrServer solr = new HttpSolrServer(MyPropertiesUtil.getMyProperty("solr.properties", "solr_url"));
		// 配置xml解析器
		solr.setParser(new XMLResponseParser());

		// SolrQuery 可以代替页面上图形化界面的操作
		SolrQuery solrQuery = new SolrQuery();
		// 设置搜索内容
		solrQuery.setQuery("combine_item:" + keywords);
		// 设置显示行数
		solrQuery.setRows(50);

		QueryResponse query = null;
		try {
			// 把Request-Handler (qt) 转化成java代码
			query = solr.query(solrQuery);// QueryResponse也是文本类型的，也需要转化
		} catch (SolrServerException e) {
			e.printStackTrace();
		} 
		list_sku = query.getBeans(KEYWORDS_T_MALL_SKU.class);

		return list_sku;
	}
}
