package com.zht.test;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.zht.bean.KEYWORDS_T_MALL_SKU;
import com.zht.factory.MySqlSessionFacory;
import com.zht.mapper.ClassMapper;
import com.zht.util.MyPropertiesUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		SqlSessionFactory sqlFactory = MySqlSessionFacory.getSqlFactory();

		ClassMapper mapper = sqlFactory.openSession().getMapper(ClassMapper.class);
		
		List<KEYWORDS_T_MALL_SKU> list_sku = mapper.select_list_by_flbh2(28);
		
		System.out.println(list_sku);
		
		//向solr中导入sku数据
		//在项目中所有地址和远程访问文件要写在配置文件中
		//创建solr服务
		HttpSolrServer solr = new HttpSolrServer(MyPropertiesUtil.getMyProperty("solr.properties", "solr_url"));
		//配置xml解析器
		solr.setParser(new XMLResponseParser());
		//把sku数据添加到solr中
		solr.addBeans(list_sku);
		//提交
		solr.commit();
		
		//SolrQuery 可以代替页面上图形化界面的操作
		SolrQuery solrQuery = new SolrQuery();
		//设置搜索内容
		solrQuery.setQuery("sku_mch:小明");
		//设置显示行数
		solrQuery.setRows(50);
		
		//把Request-Handler (qt) 转化成java代码
		QueryResponse query = solr.query(solrQuery); //QueryResponse也是文本类型的，也需要转化
		//把文本转化为集合
		List<KEYWORDS_T_MALL_SKU> beans = query.getBeans(KEYWORDS_T_MALL_SKU.class);
		
		System.out.println(beans);
		
		
	}

}
