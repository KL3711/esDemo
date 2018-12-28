package es.conn;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticConn {
	
	@SuppressWarnings("unchecked")
	public static TransportClient client(){
		try {
			Settings settings = Settings.builder().put("cluster.name", "my-application").build();
		
//			InetAddress addr101 = InetAddress.getByName("172.30.11.101");
//			InetAddress addr102 = InetAddress.getByName("172.30.11.102");
//			InetAddress addr103 = InetAddress.getByName("172.30.11.103");
//			
//			TransportClient client = new PreBuiltTransportClient(settings);
//			
//			client.addTransportAddress(new InetSocketTransportAddress(addr101, 9300))
//			  	  .addTransportAddress(new InetSocketTransportAddress(addr102, 9300))
//			      .addTransportAddress(new InetSocketTransportAddress(addr103, 9300));
			
			TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
			InetAddress local = InetAddress.getByName("127.0.0.1");
			client.addTransportAddress(new InetSocketTransportAddress(local, 9300));
			
			
			return client;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
