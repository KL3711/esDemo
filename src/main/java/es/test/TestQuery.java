package es.test;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import es.conn.ElasticConn;

public class TestQuery {
	
	public static void main(String[] args) {
		Client client = ElasticConn.client();
		
//		QueryBuilder qb = QueryBuilders.boolQuery()
//			.must(QueryBuilders.termQuery("fpdm", "1100143130"))
//			.must(QueryBuilders.termQuery("fphm", "01137502"));
//		
//		/***∑¢∆±∫≈¬Î¥˙¬Î≤È—Ø**/
//		SearchResponse res = client.prepareSearch()
//			.setIndices("dzdz")
//			.setTypes("dzdz_fpxx_zzsfp")
//			.setQuery(qb)
//			.get();
//		SearchHit[] hits = res.getHits().getHits();
//		for(SearchHit hit : hits){
//			System.out.println(hit.getSourceAsString());
//		}
		
		QueryBuilder qb = 
				QueryBuilders.matchQuery("dzdz_hwxx_zzsfp.hwmc", "÷·≥–");
//		QueryBuilder nestedQb = 
//				QueryBuilders.nestedQuery("dzdz_hwxx_zzsfp", qb, ScoreMode.None);
		
		SearchResponse res = client.prepareSearch()
				.setIndices("dzdz")
				.setTypes("dzdz_fpxx_zzsfp")
				.setQuery(qb)
				.get();
		SearchHit[] hits = res.getHits().getHits();
		System.out.println(hits[0].getSourceAsString());
		
		
		client.close();
	}
	
}
