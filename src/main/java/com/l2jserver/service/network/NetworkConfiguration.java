package com.l2jserver.service.network;

import java.net.InetSocketAddress;

import com.l2jserver.service.configuration.Configuration;
import com.l2jserver.service.configuration.Configuration.ConfigurationName;

@ConfigurationName("network")
public interface NetworkConfiguration extends Configuration {
	// TODO set default value
	/**
	 * Get the server listen address
	 * 
	 * @return the listen address
	 */
	@ConfigurationPropertyGetter(name = "listen", defaultValue = "0.0.0.0:7777")
	InetSocketAddress getListenAddress();

	/**
	 * Set the server listen address
	 * 
	 * @param addr
	 *            the listen address
	 */
	@ConfigurationPropertySetter(name = "listen")
	void setListenAddress(InetSocketAddress addr);
}