package com.wirelust.aa.services;

import java.net.URL;

import com.wirelust.aa.services.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Date: 31-Oct-2016
 *
 * @author T. Curran
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTest {

	@Spy
	Configuration config = new Configuration();

	@Before
	public void init() throws Exception {
		URL configUrl = this.getClass().getResource("/defaults.properties");
		System.setProperty(Configuration.ENV_FILE_NAME, configUrl.toURI().getPath());
		config.init();
	}

	@Test
	public void shouldLoadProperties() {
		assertTrue(config.isLoaded());
	}

	@Test
	public void shouldFailWhenFileIsNull() {
		config = new Configuration();
		System.clearProperty(Configuration.ENV_FILE_NAME);
		config.init();
		assertFalse(config.isLoaded());
	}

	@Test
	public void shouldGetNullForNonSetting() {
		assertEquals(null, config.getSetting("test.nonexistant"));
	}

	@Test
	public void shouldBeAbleToReadConfigString() {
		assertEquals("value1", config.getSetting("test.key"));
	}

	@Test
	public void shouldBeAbleToReadConfigStringOrDefault() {
		assertEquals("alternative", config.getSetting("test.key.empty", "alternative"));
		assertEquals("value1", config.getSetting("test.key", "alternative"));
		assertEquals("alternative", config.getSetting("test.key.nonexistant", "alternative"));
	}

	@Test
	public void shouldBeAbleToReadConfigInteger() {
		assertEquals(456345, config.getSettingInt("test.integer"));
		assertEquals(0, config.getSettingInt("test.integer.invalid"));
	}

	@Test
	public void shouldBeAbleToReadConfigIntegerOrDefault() {
		assertEquals(456345, config.getSettingInt("test.integer", 1234));
		assertEquals(1234, config.getSettingInt("test.integer.nonexistant", 1234));
	}

	@Test
	public void shouldBeAbleToReadConfigBoolTrue() {
		assertEquals(true, config.getSettingBool("test.bool.true"));
		assertEquals(true, config.getSettingBool("test.bool.on"));
		assertEquals(true, config.getSettingBool("test.bool.1"));
		assertEquals(true, config.getSettingBool("test.bool.yes"));
	}

	@Test
	public void shouldBeAbleToReadConfigBoolFalse() {
		assertEquals(false, config.getSettingBool("test.bool.false"));
		assertEquals(false, config.getSettingBool("test.bool.off"));
		assertEquals(false, config.getSettingBool("test.bool.0"));
		assertEquals(false, config.getSettingBool("test.bool.no"));
	}

	@Test
	public void shouldBeAbleToReadConfigBoolOrDefault() {
		assertEquals(true, config.getSettingBool("test.bool.true", false));
		assertEquals(false, config.getSettingBool("test.bool.nonexistant", false));
	}

	@Test
	public void shouldUseDefaultWhenBoolIsInvalid() {
		assertEquals(true, config.getSettingBool("test.bool.invalid", true));
		assertEquals(false, config.getSettingBool("test.bool.invalid", false));
	}

}
