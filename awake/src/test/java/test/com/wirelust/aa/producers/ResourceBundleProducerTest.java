package test.com.wirelust.aa.producers;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.inject.Inject;

import com.wirelust.aa.locales.UTF8ResourceBundleControl;
import com.wirelust.aa.producers.ResourceBundleProducer;
import com.wirelust.aa.qualifiers.ClasspathResource;
import com.wirelust.aa.qualifiers.Localization;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Date: 24-Apr-2016
 *
 * @author T. Curran
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(ResourceBundleProducer.class)
public class ResourceBundleProducerTest {

	private static final String APPLICATION_NAME = "Always Awake Server";

	@Inject
	@ClasspathResource("defaults.properties")
	Properties defaultProperties;

	@Inject
	@Localization
	ResourceBundle localization;

	@Inject
	@ClasspathResource("invalid.properties")
	Properties invalidProperties;

	@Test
	public void shouldBeAbleToLoadResource() {
		Assert.assertEquals("default", defaultProperties.getProperty("applicationSetting"));
	}

	@Test
	public void shouldBeAbleToReadLocalization() {
		Assert.assertEquals(APPLICATION_NAME, localization.getString("application.name"));
	}

	@Test
	public void invalidPropertiesShouldNotLoad() {
		Assert.assertNull(invalidProperties.getProperty("applicationSetting"));
	}

	@Test
	public void shouldBeAbleToloadLocaleWithReload() throws Exception {
		UTF8ResourceBundleControl utf8ResourceBundleControl = new UTF8ResourceBundleControl();
		ResourceBundle loadedLocale = utf8ResourceBundleControl.newBundle("com.wirelust.aa.locales.I18n",
			Locale.US, "java.properties", this.getClass().getClassLoader(), true);
		Assert.assertEquals(APPLICATION_NAME, loadedLocale.getString("application.name"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowClassIllegalArgumentExceptionOnJavaClassFormat() throws Exception {
		UTF8ResourceBundleControl utf8ResourceBundleControl = new UTF8ResourceBundleControl();

		utf8ResourceBundleControl.newBundle("test.com.wirelust.aa.util.TestNonResourceBundle",
			Locale.US, "java.class", this.getClass().getClassLoader(), false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArguementExceptionOnInvalidFormat() throws Exception {
		UTF8ResourceBundleControl utf8ResourceBundleControl = new UTF8ResourceBundleControl();

		utf8ResourceBundleControl.newBundle("test.com.wirelust.aa.util.TestResourceBundle",
			Locale.US, "invalid.format", this.getClass().getClassLoader(), false);
	}

}
