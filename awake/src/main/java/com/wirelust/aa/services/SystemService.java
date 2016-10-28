package com.wirelust.aa.services;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;

import com.wirelust.aa.qualifiers.ClasspathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 02-Apr-2015
 *
 * @author T. Curran
 */
@Named
@Singleton
public class SystemService implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemService.class);

	private Properties versionProperties;

	private String hostName = null;

	@Inject
	public SystemService(
		@ClasspathResource("version.properties")
		Properties versionProperties
	) {
		this.versionProperties = versionProperties;
	}

	public String getHostName() {
		if (hostName != null) {
			return hostName;
		}

		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();
		} catch (UnknownHostException une) {
			LOGGER.error("unable to get host name", une);
			hostName = "unknown";
		}

		return hostName;
	}

	public String getVersionAttribute(String attribute) {
		if (versionProperties == null) {
			return null;
		}
		return versionProperties.getProperty(attribute);
	}

}
