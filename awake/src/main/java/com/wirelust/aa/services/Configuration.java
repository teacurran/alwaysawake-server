package com.wirelust.aa.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wirelust.aa.exceptions.ServiceException;
import com.wirelust.aa.qualifiers.ClasspathResource;
import com.wirelust.aa.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 14-03-2015
 *
 * @author T. Curran
 */
@Named
@ApplicationScoped
public class Configuration implements Serializable {

	public static final String ENV_FILE_NAME = "app.aa.env";

	private static final long serialVersionUID = -3221266624481566406L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	boolean loaded = false;

	@Inject
	@ClasspathResource("defaults.properties")
	private Properties defaultProperties;

	private Properties configuredProperties = new Properties();

	@PostConstruct
	public void init() {
		String configFileName = System.getProperty(ENV_FILE_NAME);
		LOGGER.info("configuration {}={}", ENV_FILE_NAME, configFileName);

		if (configFileName == null) {
			LOGGER.error("{} was not specified. using defaults only", ENV_FILE_NAME);
			return;
		}

		File propertyFile = new File(configFileName);

		loadPropertyFile(propertyFile);

		LOGGER.info("env properties loaded:{}", configuredProperties.toString());
	}

	public String getSetting(final String key) {

		if (this.configuredProperties.containsKey(key)) {
			return this.configuredProperties.getProperty(key);
		}

		if (defaultProperties != null && defaultProperties.containsKey(key)) {
			return this.defaultProperties.getProperty(key);
		}
		return null;
	}

	public String getSetting(final String key, final String defaultValue) {

		final String returnValue = this.getSetting(key);
		if (StringUtils.isEmpty(returnValue)) {
			return defaultValue;
		}
		return returnValue;
	}

	public int getSettingInt(final String key) {

		final String resultString = this.getSetting(key);
		int resultInt = 0;
		try {
			resultInt = Integer.parseInt(resultString);
		} catch (final NumberFormatException e) {
			// do nothing
			LOGGER.warn("Attempt to cast setting as int failed. key:'{}'", key);
		}
		return resultInt;
	}

	public int getSettingInt(final String key, final int defaultValue) {

		final String resultString = this.getSetting(key);
		int resultInt = 0;
		try {
			resultInt = Integer.parseInt(resultString);
		} catch (final NumberFormatException e) {
			resultInt = defaultValue;
		}
		return resultInt;
	}

	public boolean getSettingBool(final String key) {
		return getSettingBool(key, false);
	}

	public boolean getSettingBool(final String key, final boolean defaultValue) {

		String resultString = getSetting(key);

		if ("1".equalsIgnoreCase(resultString) || "yes".equalsIgnoreCase(resultString) || "true".equalsIgnoreCase
			(resultString) || "on".equalsIgnoreCase(resultString)) {
			return true;
		} else if ("0".equalsIgnoreCase(resultString) || "no".equalsIgnoreCase(resultString) || "false"
			.equalsIgnoreCase(resultString) || "off".equalsIgnoreCase(resultString)) {
			return false;
		}
		return defaultValue;
	}

	public boolean isLoaded() {
		return loaded;
	}

	private void loadPropertyFile(File propertyFile) {
		try (InputStream inputStream = new FileInputStream(propertyFile)) {
			configuredProperties.load(inputStream);
			loaded = true;
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

}


