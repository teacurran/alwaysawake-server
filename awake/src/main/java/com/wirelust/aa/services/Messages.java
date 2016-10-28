package com.wirelust.aa.services;

import java.util.ResourceBundle;
import javax.inject.Inject;

import com.wirelust.aa.qualifiers.Localization;

/**
 * Date: 14-03-2015
 *
 * @author T. Curran
 */
public class Messages {

	private ResourceBundle locale;

	@Inject
	public Messages(@Localization ResourceBundle locale) {
		this.locale = locale;
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @param params
	 * @return
	 */
	public String getMessage(
			final String key,
			final String defaultValue,
			final Object... params) {

		if (locale.containsKey(key)) {
			if (params == null) {
				return locale.getString(key);
			}
			return String.format(locale.getString(key), params);
		}
		return defaultValue;
	}

	public String getMessage(
			final String key) {

		return this.getMessage(key, null);
	}

}
