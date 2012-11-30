package test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;

public class CustomLocalPortTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClientProtocolException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {

		HttpUtil util = new HttpUtil();
		for (int i = 0; i < 10; i++) {
			new Thread(new T(util)).start();
		}

		Thread.sleep(2000);
	}
}

class HttpUtil {

	HttpUtil() {
		Protocol http = new Protocol("http",
				MyProtocolSocketFactory.getSocketFactory(), 80);
		Protocol.registerProtocol("http", http);
	}

	HttpClient getClient() {
		HttpClient client = new HttpClient();
		HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
		client.setHttpConnectionManager(httpConnectionManager);
		return client;
	}
}

class T implements Runnable {
	HttpUtil util = null;

	public T(HttpUtil util) {
		this.util = util;
	}

	public void run() {
		HttpClient client = util.getClient();
		String uri = "http://localhost";
		GetMethod get = new GetMethod(uri);
		try {
			client.executeMethod(get);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
	}

}