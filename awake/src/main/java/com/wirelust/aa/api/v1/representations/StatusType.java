package com.wirelust.aa.api.v1.representations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Date: 31-Oct-2016
 *
 * @author T. Curran
 */
@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonRootName("user")
public class StatusType {

	private String productName;
	private String productVersion;
	private String buildNumber;
	private String builtBy;
	private String buildJvm;
	private String buildDate;
	private String gitCommitSha;
	private String hostName;

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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}
