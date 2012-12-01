package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HttpLocalPortConfigTest {

	@Test
	public void setup() {
		HttpLocalPortConfig.getInstance().reset();
	}

	@Test
	public void testGetAvailableLocalPort() throws InterruptedException {
		assertEquals(1099, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());
	}

	@Test
	public void testHttpLocalPortConfig() throws Exception {

		assertEquals(1099, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());
		HttpLocalPortConfig.getInstance().releaseLocalPort(1000);
		assertEquals(1100, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());
		assertEquals(1000, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());

		HttpLocalPortConfig.getInstance().releaseLocalPort(1100);
		HttpLocalPortConfig.getInstance().releaseLocalPort(1099);

		assertEquals(1100, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());
		assertEquals(1099, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());
	}

}
