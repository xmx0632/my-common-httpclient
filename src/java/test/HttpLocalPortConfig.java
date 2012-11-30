package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpLocalPortConfig {
	private static final Log LOG = LogFactory.getLog(HttpLocalPortConfig.class);

	private static HttpLocalPortConfig instance = null;

	private BlockingQueue<Integer> availableLocalPorts = new LinkedBlockingQueue<Integer>();

	public static HttpLocalPortConfig getInstance() {
		if (instance == null) {
			synchronized (HttpLocalPortConfig.class) {
				HttpLocalPortConfig inst = instance;
				if (inst == null) {
					synchronized (HttpLocalPortConfig.class) {
						instance = new HttpLocalPortConfig();
					}
				}
			}
		}
		return instance;
	}

	private HttpLocalPortConfig() {
		InputStream inStream = HttpLocalPortConfig.class.getClassLoader()
				.getResourceAsStream("localport.properties");
		Properties p = new Properties();
		try {
			p.load(inStream);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		String range = p.getProperty("localPortRange");
		LOG.debug("range:" + range);
		String[] ranges = range.split("\\-");
		LOG.debug("range0:" + ranges[0]);
		LOG.debug("range1:" + ranges[1]);
		Integer start = 10240;
		Integer end = 20480;
		try {
			start = Integer.valueOf(ranges[0]);
			end = Integer.valueOf(ranges[1]);
		} catch (NumberFormatException e) {
			LOG.error("localport.properties config error", e);
		}
		for (int i = start; i < end; i++) {
			availableLocalPorts.add(i);
		}
		LOG.debug("availableLocalPorts size:" + availableLocalPorts.size());
	}

	public int getAvailableLocalPort() throws InterruptedException {
		Integer localPort = availableLocalPorts.take();
		LOG.debug("getAvailableLocalPort:" + localPort);
		return localPort;
	}

	public void releaseLocalPort(int localPort) {
		LOG.debug("releaseLocalPort:" + localPort);
		availableLocalPorts.add(localPort);
	}

}
