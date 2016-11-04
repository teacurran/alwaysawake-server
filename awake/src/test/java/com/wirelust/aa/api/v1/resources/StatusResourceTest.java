package com.wirelust.aa.api.v1.resources;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.wirelust.aa.api.helpers.StatusHelper;
import com.wirelust.aa.api.v1.resources.StatusResource;
import com.wirelust.aa.producers.ResourceBundleProducer;
import com.wirelust.aa.services.SystemService;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Date: 31-Oct-2016
 *
 * @author T. Curran
 */
@RunWith(CdiRunner.class)
@AdditionalClasses({ResourceBundleProducer.class, StatusHelper.class, SystemService.class})
public class StatusResourceTest {

	@Inject
	StatusResource statusResource;

	@Test
	public void shouldBeAbleToGetStatus() {
		Response response = statusResource.getBuildStatus();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
}
