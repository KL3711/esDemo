package es.test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.ingest.IngestActionForwarder;
import org.elasticsearch.action.ingest.PutPipelineAction;
import org.elasticsearch.action.ingest.PutPipelineRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.index.mapper.Mapping;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.ingest.CompoundProcessor;
import org.elasticsearch.ingest.IngestInfo;
import org.elasticsearch.ingest.Pipeline;
import org.elasticsearch.ingest.Processor;
import org.elasticsearch.ingest.ProcessorInfo;
import org.elasticsearch.monitor.process.ProcessService;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.yaml.snakeyaml.events.MappingStartEvent;

import net.sf.json.JSONObject;
import oracle.sql.TIMESTAMP;

public class Test {

	public static void main(String[] args) throws Exception {
		
//		Settings settings = Settings.builder()
//		        .put("client.transport.sniff", true)
//		        .put("client.transport.ignore_cluster_name", true).build();
		Settings settings = Settings.builder().put("cluster.name", "my-application").build();
		TransportClient client = new PreBuiltTransportClient(settings);
		
//		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
//		InetAddress local = InetAddress.getByName("127.0.0.1");
//		client.addTransportAddress(new InetSocketTransportAddress(local,9300));
		
		InetAddress addr101 = InetAddress.getByName("172.30.11.101");
		InetAddress addr102 = InetAddress.getByName("172.30.11.102");
		InetAddress addr103 = InetAddress.getByName("172.30.11.103");
		
		
		client.addTransportAddress(new InetSocketTransportAddress(addr101, 9300))
			  .addTransportAddress(new InetSocketTransportAddress(addr102, 9300))
			  .addTransportAddress(new InetSocketTransportAddress(addr103, 9300));
		
		SearchResponse res = client.prepareSearch()
		.setQuery(QueryBuilders.matchAllQuery()).get();
		
		System.out.println(res.status().getStatus());
		
//		String testJson = "{\"fpdm\":1101652130,\"fphm\":\"00000001\","
//				+ "\"je\":1000.00,\"se\":170.00,"
//				+ "\"bz\":\"This is mye2016 test data\"}";
//		
//		IndexResponse res = client.prepareIndex("test", "mye2016","1").setSource(testJson).get();
//		
//		System.out.println("testJson create:" + res.status().getStatus());
		
		//nest type data
		//create index 
//		GetMappingsResponse gir = client.admin().indices().prepareGetMappings("dzdz").get();
//		System.out.println(gir.getMappings());
		
//		Map map = new HashMap();
//		System.out.println(new Date());
//		Date d = new Date(new Timestamp(System.currentTimeMillis()).getTime());
//		map.put("kprq", d);
//		IndexResponse res = client.prepareIndex("testdate","tdate").setSource(map).get();
//		
		
//		GetResponse res = client.prepareGet("testdate", "tdate", "AVw01tY83sNJPGO4N8I3").get();
//		System.out.println(res.getSource().get("kprq"));
		
//		Map json = new HashMap();
//		Date d = new Date(new Timestamp(System.currentTimeMillis()).getTime());
//		json.put("kprq", d);
//		IndexResponse res = client.prepareIndex("testdate", "tdate").setSource(json.toString()).get();
//		System.out.println(res.getId());
		
		//同步执行
//		BulkByScrollResponse bulkResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//					.filter(QueryBuilders.matchQuery("kprq", null)).source("testdate").get();
		//异步执行
//		DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//				.filter(QueryBuilders.matchQuery("kprq", "2017-05-23")).source("testdate")
//				.execute(new ActionListener<BulkByScrollResponse>() {
//					
//					public void onResponse(BulkByScrollResponse response) {
//						// TODO Auto-generated method stub
//						System.out.println("已删除：" + response.getDeleted());
//					}
//					
//					public void onFailure(Exception e) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
		client.close();
	}
	
	
}
