/*
 * This file is part of l2jserver2 <l2jserver2.com>.
 *
 * l2jserver2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * l2jserver2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with l2jserver2.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.service.network;

import java.net.InetSocketAddress;

import com.l2jserver.service.ServiceConfiguration;
import com.l2jserver.service.configuration.Configuration;
import com.l2jserver.service.configuration.XMLConfigurationService.ConfigurationXPath;

/**
 * The network {@link Configuration}
 * 
 * @author <a href="http://www.rogiel.com">Rogiel</a>
 */
public interface NetworkServiceConfiguration extends ServiceConfiguration {
	/**
	 * Get the server listen address
	 * 
	 * @return the listen address
	 */
	@ConfigurationPropertyGetter(defaultValue = "0.0.0.0:7777")
	@ConfigurationXPath("server/@listen")
	InetSocketAddress getListenAddress();

	/**
	 * Set the server listen address
	 * 
	 * @param addr
	 *            the listen address
	 */
	@ConfigurationPropertySetter
	@ConfigurationXPath("server/@listen")
	void setListenAddress(InetSocketAddress addr);
}