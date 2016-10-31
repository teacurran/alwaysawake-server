package com.wirelust.aa.api.v1.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wirelust.aa.api.helpers.StatusHelper;
import com.wirelust.aa.api.v1.representations.StatusType;

/**
 * Date: 31-Oct-2016
 *
 * @author T. Curran
 */
@Path("/status")
public class StatusResource {

	/**
	 * SystemService object to get build status from file
	 */
	@Inject
	private StatusHelper statusHelper;

	/**
	 * method to get the build status
	 *
	 * @return
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getBuildStatus() {
		final StatusType statusType = statusHelper.getStatusType();

		final Response.ResponseBuilder responseBuilder = Response.ok(statusType);
		return responseBuilder.build();
	}

}
