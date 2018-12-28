package es.test;

import java.util.Map;

import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.search.SearchHit;

import es.conn.ElasticConn;

public class TestUpdate {
	
	public static void main(String[] args) {
		Client client = ElasticConn.client();
		SearchResponse res = client.prepareSearch()
				.setIndices("dzdz")
				.setTypes("dzdz_fpxx_zzsfp")
				.setQuery(QueryBuilders.termsQuery("fpdm", "1100160130"))
				.setQuery(QueryBuilders.termsQuery("fphm", "00000003"))
				.get();
		SearchHit[] hits = res.getHits().getHits();
		/****发票勾选状态的修改****/
		/**
		 * PUT _ingest/pipeline/set-foo
			{
			  "description" : "sets foo",
			  "processors" : [ {
			      "set" : {
			        "field": "foo",
			        "value": "bar"
			      }
			  } ]
			}
			POST twitter/_update_by_query?pipeline=set-foo
		 * */
		//创建ingest pipeline set-gxzt
		
		//PutPipelineAction.INSTANCE.newRequestBuilder(client).request().
		
		System.out.println(hits[0].getId());
		BulkByScrollResponse bbsr = UpdateByQueryAction.INSTANCE.newRequestBuilder(client)
			.source("dzdz")
			.filter(QueryBuilders.idsQuery("dzdz_fpxx_zzsfp").addIds(hits[0].getId()))
			.setPipeline("set-gxzt")
			.get();
		System.out.println(bbsr.getUpdated());
		//System.out.println(bbsr.getBulkFailures().size());
		/**********按id进行更新***********/
//		Map<String, Object> source = hits[0].getSource();
//		source.put("gxzt", "1");
//		UpdateResponse ures = client.prepareUpdate("dzdz", "dzdz_fpxx_zzsfp", hits[0].getId())
//				.setDoc(source).get();
//		System.out.println(ures.status().getStatus());
		
	}
	
}
