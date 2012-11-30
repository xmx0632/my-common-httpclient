/*
 * ====================================================================
 *
 *  Copyright 2002-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The default class for creating protocol sockets. This class just uses the
 * {@link java.net.Socket socket} constructors.
 * 
 * @author xmx0632
 * 
 */
public class MyProtocolSocketFactory extends DefaultProtocolSocketFactory {

	private static final Log LOG = LogFactory
			.getLog(MyProtocolSocketFactory.class);

	/**
	 * The factory singleton.
	 */
	private static final MyProtocolSocketFactory factory = new MyProtocolSocketFactory();

	/**
	 * Gets an singleton instance of the DefaultProtocolSocketFactory.
	 * 
	 * @return a DefaultProtocolSocketFactory
	 */
	static MyProtocolSocketFactory getSocketFactory() {
		return factory;
	}

	/**
	 * Constructor for DefaultProtocolSocketFactory.
	 */
	public MyProtocolSocketFactory() {
		super();
	}

	/**
	 * @see #createSocket(java.lang.String,int,java.net.InetAddress,int)
	 */
	public Socket createSocket(String host, int port, InetAddress localAddress,
			int localPort) throws IOException, UnknownHostException {
		int availableLocalPort = localPort;
		if (localPort == 0) {
			// 从指定的localPort端口范围中选取可用端口
			try {
				availableLocalPort = getAvailableLocalPort();
			} catch (InterruptedException e) {
				// GOD BLESS U
				LOG.error("get available local port failed!", e);
				availableLocalPort = 0;
			}
		}
		return new Socket(host, port, localAddress, availableLocalPort);
	}

	private int getAvailableLocalPort() throws InterruptedException {
		return HttpLocalPortConfig.getInstance().getAvailableLocalPort();
	}

	/**
	 * All instances of DefaultProtocolSocketFactory are the same.
	 */
	public boolean equals(Object obj) {
		return ((obj != null) && obj.getClass().equals(
				MyProtocolSocketFactory.class));
	}

	/**
	 * All instances of DefaultProtocolSocketFactory have the same hash code.
	 */
	public int hashCode() {
		return MyProtocolSocketFactory.class.hashCode();
	}

}
