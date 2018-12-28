package es.test;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;

import es.conn.ElasticConn;

public class TestSort {
	
	public static void main(String[] args) {
		TransportClient client = ElasticConn.client();
		
		String sql = "select * from dzdz.dzdz_fpxx_zzsfp where fpzt='0'";
		
		
		client.prepareSearch("dzdz").setTypes("dzdz_fpxx_zzsfp")
			.setQuery(QueryBuilders.matchAllQuery());
		
		
		
		
		client.close();
	}
	
}
