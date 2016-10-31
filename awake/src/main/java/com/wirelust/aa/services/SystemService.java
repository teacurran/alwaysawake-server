package com.wirelust.aa.services;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.wirelust.aa.qualifiers.ClasspathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 02-Apr-2015
 *
 * @author T. Curran
 */
@ApplicationScoped
public class SystemService implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemService.class);

	private Properties versionProperties;

	/**
	 * variable to hold host name
	 */
	private transient String hostName;

	private String productName;
	private String productVersion;
	private String buildNumber;
	private String builtBy;
	private String buildJvm;
	private String buildDate;
	private String gitCommitSha;

	public SystemService() {
		// no arg constructore required by CDI
	}

	@Inject
	public SystemService(
		@ClasspathResource("version.properties")
		Properties versionProperties
	) {
		this.versionProperties = versionProperties;
	}

	@PostConstruct
	public void init() {
		setProductName(getVersionAttribute("product_name"));
		setProductVersion(getVersionAttribute("product_version"));
		setBuildNumber(getVersionAttribute("build_number"));
		setBuiltBy(getVersionAttribute("built_by"));
		setBuildJvm(getVersionAttribute("build_jvm"));
		setBuildDate(getVersionAttribute("build_date"));
		setGitCommitSha(getVersionAttribute("git_commit_sha"));
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

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getBuiltBy() {
		return builtBy;
	}

	public void setBuiltBy(String builtBy) {
		this.builtBy = builtBy;
	}

	public String getBuildJvm() {
		return buildJvm;
	}

	public void setBuildJvm(String buildJvm) {
		this.buildJvm = buildJvm;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getGitCommitSha() {
		return gitCommitSha;
	}

	public void setGitCommitSha(String gitCommitSha) {
		this.gitCommitSha = gitCommitSha;
	}

	/**
	 * method to get version property values
	 *
	 * @param attribute
	 * @return version property
	 */
	private String getVersionAttribute(final String attribute) {
		String property = null;
		if (versionProperties != null) {
			property = versionProperties.getProperty(attribute);

		}
		return property;
	}

}
