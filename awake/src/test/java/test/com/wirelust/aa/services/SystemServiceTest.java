package test.com.wirelust.aa.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.Properties;

import com.wirelust.aa.services.SystemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Date: 31-Oct-2016
 *
 * @author T. Curran
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemServiceTest {

	@Spy
	Properties versionProperties;

	@InjectMocks
	SystemService systemService;

	@Before
	public void init() throws Exception {
		URL versionPropertyUrl = this.getClass().getResource("/version.properties");
		File propertyFile = new File(versionPropertyUrl.toURI().getPath());
		try (InputStream defaultInputStream = new FileInputStream(propertyFile)) {
			versionProperties.load(defaultInputStream);
		}

		systemService.init();
	}

	@Test
	public void shouldBeAbleToGetHostname() throws Exception {
		Assert.assertEquals(InetAddress.getLocalHost().getHostName(), systemService.getHostName());
	}

	@Test
	public void shouldBeAbleToGetProductName() throws Exception {
		Assert.assertEquals("awake", systemService.getProductName());
	}

	@Test
	public void shouldBeAbleToGetProductVersion() throws Exception {
		Assert.assertNotNull(systemService.getProductVersion());
	}

	@Test
	public void shouldBeAbleToGetBuildNumber() throws Exception {
		Assert.assertNotNull(systemService.getBuildNumber());
	}

	@Test
	public void shouldBeAbleToGetBuiltBy() throws Exception {
		Assert.assertNotNull(systemService.getBuiltBy());
	}

	@Test
	public void shouldBeAbleToGetBuiltJvm() throws Exception {
		Assert.assertNotNull(systemService.getBuildJvm());
	}

	@Test
	public void shouldBeAbleToGetBuildDate() throws Exception {
		Assert.assertNotNull(systemService.getBuildDate());
	}

	@Test
	public void shouldBeAbleToGetGitCommitSha() throws Exception {
		Assert.assertNotNull(systemService.getGitCommitSha());
	}
}
