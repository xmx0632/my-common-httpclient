package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HttpLocalPortConfigTest {

	@Test
	public void testGetInstance() {
		HttpLocalPortConfig.getInstance();
	}

	@Test
	public void testGetAvailableLocalPort() throws InterruptedException {
		assertEquals(1099, HttpLocalPortConfig.getInstance()
				.getAvailableLocalPort());
	}

}
