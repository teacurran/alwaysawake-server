package com.wirelust.aa.api.helpers;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.wirelust.aa.api.v1.representations.StatusType;
import com.wirelust.aa.services.SystemService;

/**
 * Date: 31-Oct-2016
 *
 * @author T. Curran
 */
@Dependent
public class StatusHelper {

	@Inject
	SystemService systemService;

	public StatusType getStatusType() {
		StatusType statusType = new StatusType();
		statusType.setGitCommitSha(systemService.getGitCommitSha());
		statusType.setBuildDate(systemService.getBuildDate());
		statusType.setBuildJvm(systemService.getBuildJvm());
		statusType.setBuiltBy(systemService.getBuiltBy());
		statusType.setBuildNumber(systemService.getBuildNumber());
		statusType.setProductName(systemService.getProductName());
		statusType.setProductVersion(systemService.getProductVersion());
		statusType.setHostName(systemService.getHostName());

		return statusType;
	}
}
