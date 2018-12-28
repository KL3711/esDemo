package es.impdata;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import es.conn.Conn;
import es.conn.ElasticConn;
import es.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ImpData2 {

	public void impData(){
		
		JSONObject textType = new JSONObject();
		textType.put("type", "text");
		JSONObject dateType = new JSONObject();
		dateType.put("type", "date");
		JSONObject doubleType = new JSONObject();
		doubleType.put("type", "double");
		
		
		Connection conn = Conn.conn();
		TransportClient client = ElasticConn.client();
		
		try {
			
			/*************createStructure*************/
			/*String zzsfp_structure = "select t.COLUMN_NAME,t.DATA_TYPE from user_tab_columns t where t.TABLE_NAME='DZDZ_FPXX_ZZSFP'";
			ResultSet resultSetFp = conn.createStatement().executeQuery(zzsfp_structure);
			
			JSONObject zzsfpJson = new JSONObject();
			JSONObject zzsfpPropJson = new JSONObject();
			while(resultSetFp.next()){
				if("VARCHAR2".equals(resultSetFp.getString("DATA_TYPE"))){
					zzsfpPropJson.put(resultSetFp.getString("COLUMN_NAME").toLowerCase(), textType);
				}else if("CHAR".equals(resultSetFp.getString("DATA_TYPE"))){
					zzsfpPropJson.put(resultSetFp.getString("COLUMN_NAME").toLowerCase(), textType);
				}else if("DATE".equals(resultSetFp.getString("DATA_TYPE"))){
					zzsfpPropJson.put(resultSetFp.getString("COLUMN_NAME").toLowerCase(), dateType);
				}else if("NUMBER".equals(resultSetFp.getString("DATA_TYPE"))){
					zzsfpPropJson.put(resultSetFp.getString("COLUMN_NAME").toLowerCase(), doubleType);
				}
			}
			
			String hwxx_structure = "select t.COLUMN_NAME,t.DATA_TYPE from user_tab_columns t where t.TABLE_NAME='DZDZ_HWXX_ZZSFP'";
			ResultSet resultSetHw = conn.createStatement().executeQuery(hwxx_structure);
			JSONObject hwxxJson = new JSONObject();
			JSONObject hwxxPropJson = new JSONObject();
			while(resultSetHw.next()){
				if("VARCHAR2".equals(resultSetHw.getString("DATA_TYPE"))){
					hwxxPropJson.put(resultSetHw.getString("COLUMN_NAME").toLowerCase(), textType);
				}else if("CHAR".equals(resultSetHw.getString("DATA_TYPE"))){
					hwxxPropJson.put(resultSetHw.getString("COLUMN_NAME").toLowerCase(), textType);
				}else if("DATE".equals(resultSetHw.getString("DATA_TYPE"))){
					hwxxPropJson.put(resultSetHw.getString("COLUMN_NAME").toLowerCase(), dateType);
				}else if("NUMBER".equals(resultSetHw.getString("DATA_TYPE"))){
					hwxxPropJson.put(resultSetHw.getString("COLUMN_NAME").toLowerCase(), doubleType);
				}
			}
			hwxxJson.put("type", "nested");
			hwxxJson.put("properties", hwxxPropJson);
			
			zzsfpPropJson.put("dzdz_hwxx_zzsfp", hwxxJson);
			zzsfpJson.put("properties", zzsfpPropJson);
			
			System.out.println(zzsfpJson);
			/*************createStructure*************/
//			TransportClient client = ElasticConn.client();
//			CreateIndexResponse cir = client.admin().indices().prepareCreate("dzdz").addMapping("dzdz_fpxx_zzsfp", zzsfpJson.toString()).get();
//			if(cir.isAcknowledged()){
//				System.out.println("/dzdz/dzdz_fpxx_zzsfp create success!");
//			}
			
			
			/************createData***************/
			//for(int i = 0;i<3;i++){
			System.out.println(new Timestamp(System.currentTimeMillis()));
			String zzsfpsql = "select * from dzdz_fpxx_zzsfp";
			String hwxxsql = "select * from dzdz_hwxx_zzsfp where fpdm=? and fphm=?";
			PreparedStatement ps = conn.prepareStatement(hwxxsql);
			ResultSet zzsfpData = conn.createStatement().executeQuery(zzsfpsql);
			
			
			//1000 item bulk submit
			//BulkRequestBuilder brb = client.prepareBulk();
			int count = 1;
			while(zzsfpData.next()){
				Map<String,Object> zzsfp = new HashMap<String,Object>();
				String fphm = zzsfpData.getString("FPHM");
				String fpdm = zzsfpData.getString("FPDM");
				
				zzsfp.put("fphm", fphm);
				zzsfp.put("fpdm", fpdm);
				//System.out.println(fpdm + fphm);
				zzsfp.put("kjlx_dm", zzsfpData.getString("KJLX_DM"));
				zzsfp.put("xxbbh", zzsfpData.getString("XXBBH"));
				zzsfp.put("slbs", zzsfpData.getString("SLBS"));
				zzsfp.put("sbyt_dm", zzsfpData.getString("SBYT_DM"));
				zzsfp.put("skssq", zzsfpData.getString("SKSSQ"));
				zzsfp.put("fpzt_whsj", transferTimestamp(zzsfpData.getTimestamp("FPZT_WHSJ")));
				zzsfp.put("fpzt_ywsj", transferTimestamp(zzsfpData.getTimestamp("FPZT_YWSJ")));
				zzsfp.put("fpzt_dm", zzsfpData.getString("FPZT_DM"));
				zzsfp.put("spbmbbh", zzsfpData.getString("SPBMBBH"));
				zzsfp.put("cycs", zzsfpData.getString("CYCS"));
				zzsfp.put("data_category", zzsfpData.getString("DATA_CATEGORY"));
				zzsfp.put("bdlx_dm", zzsfpData.getString("BDLX_DM"));
				zzsfp.put("hcjg_dm", zzsfpData.getString("HCJG_DM"));
				zzsfp.put("hcrq", transferTimestamp(zzsfpData.getTimestamp("HCRQ")));
				zzsfp.put("hchqrq", transferTimestamp(zzsfpData.getTimestamp("HCHQRQ")));
				zzsfp.put("hczt_dm", zzsfpData.getString("HCZT_DM"));
				zzsfp.put("zjxfbz", zzsfpData.getString("ZJXFBZ"));
				zzsfp.put("zjxflsh", zzsfpData.getString("ZJXFLSH"));
				zzsfp.put("yqsj", transferTimestamp(zzsfpData.getTimestamp("YQSJ")));
				zzsfp.put("yqjg_dm", zzsfpData.getString("YQJG_DM"));
				zzsfp.put("sbsq_jgqrsj", transferTimestamp(zzsfpData.getTimestamp("SBSQ_JGQRSJ")));
				zzsfp.put("sbsq_qrjg_dm", zzsfpData.getString("SBSQ_QRJG_DM"));
				zzsfp.put("sbsq_bdjg_dm", zzsfpData.getString("SBSQ_BDJG_DM"));
				zzsfp.put("sbsqlsh", zzsfpData.getString("SBSQLSH")==null?"":zzsfpData.getString("SBSQLSH"));
				zzsfp.put("skmyz_yzrq", transferTimestamp(zzsfpData.getTimestamp("SKMYZ_YZRQ")));
				zzsfp.put("skmyz_yzjg_dm", zzsfpData.getString("SKMYZ_YZJG_DM"));
				zzsfp.put("fpbd_bdrq", transferTimestamp(zzsfpData.getTimestamp("FPBD_BDRQ")));
				zzsfp.put("fpbd_bflx_dm", zzsfpData.getString("FPBD_BFLX_DM"));
				zzsfp.put("fpbd_bdjg_dm", zzsfpData.getString("FPBD_BDJG_DM"));
				zzsfp.put("rzdkl_rzyf", zzsfpData.getString("RZDKL_RZYF"));
				zzsfp.put("rzdkl_bdrq", transferTimestamp(zzsfpData.getTimestamp("RZDKL_BDRQ")));
				zzsfp.put("rzdkl_bflx_dm", zzsfpData.getString("RZDKL_BFLX_DM"));
				zzsfp.put("rzdkl_bdjg_dm", zzsfpData.getString("RZDKL_BDJG_DM"));
				zzsfp.put("wfqfbz", zzsfpData.getString("WFQFBZ"));
				zzsfp.put("ydfpbz", zzsfpData.getString("YDFPBZ"));
				zzsfp.put("gf_qxswjg_dm", zzsfpData.getString("GF_QXSWJG_DM"));
				zzsfp.put("gf_dsswjg_dm", zzsfpData.getString("GF_DSSWJG_DM"));
				zzsfp.put("gf_sjswjg_dm", zzsfpData.getString("GF_SJSWJG_DM"));
				zzsfp.put("xf_qxswjg_dm", zzsfpData.getString("XF_QXSWJG_DM"));
				zzsfp.put("xf_dsswjg_dm", zzsfpData.getString("XF_DSSWJG_DM"));
				zzsfp.put("xf_sjswjg_dm", zzsfpData.getString("XF_SJSWJG_DM"));
				zzsfp.put("zjqfrq", transferTimestamp(zzsfpData.getTimestamp("ZJQFRQ")));
				zzsfp.put("sjqfrq", transferTimestamp(zzsfpData.getTimestamp("SJQFRQ")));
				zzsfp.put("tslsh", zzsfpData.getString("TSLSH"));
				zzsfp.put("tsrq", transferTimestamp(zzsfpData.getTimestamp("TSRQ")));
				zzsfp.put("tspzbz", zzsfpData.getString("TSPZBZ"));
				zzsfp.put("blrq", transferTimestamp(zzsfpData.getTimestamp("BLRQ")));
				zzsfp.put("blry", zzsfpData.getString("BLRY"));
				zzsfp.put("blbz", zzsfpData.getString("BLBZ"));
				zzsfp.put("sklx_dm", zzsfpData.getString("SKLX_DM"));
				zzsfp.put("qdbz", zzsfpData.getString("QDBZ"));
				zzsfp.put("fpqm", zzsfpData.getString("FPQM"));
				zzsfp.put("bsswjg_dm", zzsfpData.getString("BSSWJG_DM"));
				zzsfp.put("bssj", transferTimestamp(zzsfpData.getTimestamp("BSSJ")));
				zzsfp.put("kpyf", zzsfpData.getString("KPYF"));
				zzsfp.put("jym", zzsfpData.getString("JYM"));
				zzsfp.put("wspzh", zzsfpData.getString("WSPZH"));
				zzsfp.put("zfsj", transferTimestamp(zzsfpData.getTimestamp("ZFSJ")));
				zzsfp.put("zfbz", zzsfpData.getString("ZFBZ"));
				zzsfp.put("xfdzdh", zzsfpData.getString("XFDZDH"));
				zzsfp.put("xfsbh", zzsfpData.getString("XFSBH"));
				zzsfp.put("xfmc", zzsfpData.getString("XFMC"));
				zzsfp.put("gfyhzh", zzsfpData.getString("GFYHZH"));
				zzsfp.put("gfdzdh", zzsfpData.getString("GFDZDH"));
				zzsfp.put("gfsbh", zzsfpData.getString("GFSBH"));
				zzsfp.put("gfmc", zzsfpData.getString("GFMC"));
				zzsfp.put("skm", zzsfpData.getString("SKM"));
				zzsfp.put("kprq", transferTimestamp(zzsfpData.getTimestamp("KPRQ")));
				zzsfp.put("kpjh", zzsfpData.getString("KPJH"));
				zzsfp.put("jqbh", zzsfpData.getString("JQBH"));
				zzsfp.put("kpr", zzsfpData.getString("KPR"));
				zzsfp.put("fhr", zzsfpData.getString("FHR"));
				zzsfp.put("skr", zzsfpData.getString("SKR"));
				zzsfp.put("bz", zzsfpData.getString("BZ"));
				zzsfp.put("jshj", zzsfpData.getString("JSHJ"));
				zzsfp.put("se", zzsfpData.getString("SE"));
				zzsfp.put("je", zzsfpData.getString("JE"));
				zzsfp.put("xfyhzh", zzsfpData.getString("XFYHZH"));
				
				ps.setString(1, fpdm);
				ps.setString(2, fphm);
				ResultSet hwxx = ps.executeQuery();
				ArrayList hwxxA = new ArrayList();
				while(hwxx.next()){
					Map<String,Object> hwxxItem = new HashMap<String,Object>();
					hwxxItem.put("ydfpbz", hwxx.getString("YDFPBZ"));
					hwxxItem.put("tsrq", transferTimestamp(hwxx.getTimestamp("TSRQ")));
					hwxxItem.put("se", hwxx.getString("SE"));
					hwxxItem.put("kpyf", hwxx.getString("KPYF"));
					hwxxItem.put("slv", hwxx.getString("SLV"));
					hwxxItem.put("sl", hwxx.getString("SL"));
					hwxxItem.put("id", hwxx.getString("ID"));
					hwxxItem.put("je", hwxx.getString("JE"));
					hwxxItem.put("dj", hwxx.getString("DJ"));
					hwxxItem.put("ggxh", hwxx.getString("GGXH"));
					hwxxItem.put("hwmc", hwxx.getString("HWMC"));
					hwxxItem.put("lslbz", hwxx.getString("LSLBZ"));
					hwxxItem.put("dw", hwxx.getString("DW"));
					hwxxItem.put("fphm", hwxx.getString("FPHM"));
					hwxxItem.put("fpdm", hwxx.getString("FPDM"));
					hwxxItem.put("qdbz", hwxx.getString("QDBZ"));
					hwxxItem.put("yhzc", hwxx.getString("YHZC"));
					hwxxItem.put("gf_sjswjg_dm", hwxx.getString("GF_SJSWJG_DM"));
					hwxxItem.put("zjxflsh", hwxx.getString("ZJXFLSH"));
					hwxxItem.put("xf_sjswjg_dm", hwxx.getString("XF_SJSWJG_DM"));
					hwxxItem.put("tslsh", hwxx.getString("TSLSH"));
					hwxxItem.put("qyspbm", hwxx.getString("QYSPBM"));
					hwxxItem.put("syyhzcbz", hwxx.getString("SYYHZCBZ"));
					hwxxItem.put("data_category", hwxx.getString("DATA_CATEGORY"));
					hwxxItem.put("spbm", hwxx.getString("SPBM"));
					
					hwxxA.add(hwxxItem);
				}
				
				zzsfp.put("dzdz_hwxx_zzsfp", hwxxA.toArray());
//				brb.add(client.prepareIndex("dzdz", "dzdz_fpxx_zzsfp").setSource(zzsfp));
				IndexResponse ir = client.prepareIndex("dzdz", "dzdz_fpxx_zzsfp").setSource(zzsfp).get();
				count++;
				if(count%100==0){
					System.out.println(new Timestamp(System.currentTimeMillis()) + "£º\tµÚ"+ count +"Ìõid£º" + ir.getId());
					
				}
//				if(count%1000==0){
//					BulkResponse br = brb.get();
//					if(br.hasFailures()){
//						System.out.println("below " + count + " has failure!");
//						System.out.println(br.buildFailureMessage() + "," + br.status().getStatus());
//					}
//					brb = client.prepareBulk();
//					System.out.println(new Timestamp(System.currentTimeMillis()) + "£º\t" + count + "has done!");
//				}
			}
//			BulkResponse lastbr = brb.get();
//			if(lastbr.hasFailures()){
//				System.out.println("below " + count + " has failure!");
//				System.out.println(lastbr.buildFailureMessage() + "," + lastbr.status().getStatus());
//			}
			//brb = client.prepareBulk();
			System.out.println(new Timestamp(System.currentTimeMillis()) + "£º\t" + count + "has done!");
			//}
			

			/************createData***************/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				client.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Date transferTimestamp(Timestamp t){
		if(t == null){
			return null;
		}
		return new Date(t.getTime());
	}
	
	public static void main(String[] args) {
		new ImpData2().impData();
	}
	
}
