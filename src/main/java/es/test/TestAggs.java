package es.test;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;

import es.conn.ElasticConn;

public class TestAggs {

	public static void main(String[] args) {
		
		Client client = ElasticConn.client();
		/***统计当月已勾选发票，数量，金额，税额合计**/
		/**
		 * 需开启字段允许聚合
		 PUT megacorp/_mapping/employee/
			{
			  "properties": {
			    "interests": { 
			      "type":     "text",
			      "fielddata": true
			    }
			  }
			}
		 * */
		/**
		 * 聚合时，精度有损失，建议金额税额为scale_float,scaling_factor:2
		 * */
		QueryBuilder qb = QueryBuilders.rangeQuery("kprq").gte("now-180d");
		AggregationBuilder ab = AggregationBuilders
				.dateHistogram("by_month").field("kprq").dateHistogramInterval(DateHistogramInterval.MONTH).format("yyyyMM")
						//按勾选状态
						.subAggregation(AggregationBuilders.terms("by_gxzt").field("gxzt")
								.subAggregation(AggregationBuilders.sum("invamounts").field("je"))
								.subAggregation(AggregationBuilders.sum("invtaxs").field("se")));
		SearchResponse sres = client.prepareSearch("dzdz").setTypes("dzdz_fpxx_zzsfp")
			.setSize(0)//不关心查到的数据内容，将size设置为0，加快查询效率
			.setQuery(qb)
			.addAggregation(ab)
			.get();
//		SearchHit[] aggHits = sres.getHits().getHits();
//		for(SearchHit hit : aggHits){
//			System.out.println(hit.getSourceAsString());
//		}
		List<? extends Histogram.Bucket> byMonthBucketList = ((Histogram)sres.getAggregations().get("by_month")).getBuckets();
		for (Histogram.Bucket byMonthBucket : byMonthBucketList) {
		    // do whatever
			List<Bucket> byGxztBucketList = ((StringTerms) byMonthBucket.getAggregations().get("by_gxzt")).getBuckets();
			for(Bucket byGxztBucket : byGxztBucketList){
				System.out.println("时间" + byMonthBucket.getKeyAsString() + ",勾选状态：" + byGxztBucket.getKeyAsString() + ",invamounts:" +  ((InternalSum)byGxztBucket.getAggregations().get("invamounts")).getValue());
			}
		}
	}
	
}
