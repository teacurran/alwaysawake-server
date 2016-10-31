package com.wirelust.aa.locales;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 09-03-2015
 *
 * @author T. Curran
 *
 * see: http://stackoverflow.com/a/4660195/1330640
 */
public class UTF8ResourceBundleControl extends ResourceBundle.Control {

	public UTF8ResourceBundleControl() {
		// default constructor because ResourceBundle.Control constructor is protected
	}

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
		throws IllegalAccessException, InstantiationException, IOException {
		String bundleName = toBundleName(baseName, locale);
		ResourceBundle bundle = null;
		if ("java.properties".equals(format)) {
			try (InputStream stream = AccessController.doPrivileged(
				new PrivilegedInputStreamAccessAction(reload, toResourceName(bundleName, "properties"), loader)
				)) {

				// Only this line is changed to make it to read properties files as UTF-8.
				bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
			} catch (PrivilegedActionException e) {
				Exception unwrappedException = e.getException();
				if (unwrappedException instanceof IOException) {
					throw (IOException) unwrappedException;
				}
				throw new IOException(e);
			}
		} else {
			throw new IllegalArgumentException("unsupported format: " + format);
		}
		return bundle;
	}

	private static class PrivilegedInputStreamAccessAction implements PrivilegedExceptionAction<InputStream> {

		final String resourceName;

		final ClassLoader classLoader;

		final boolean reloadFlag;

		private PrivilegedInputStreamAccessAction(final boolean reloadFlag,
												 final String resourceName,
												 final ClassLoader classLoader) {
			this.reloadFlag = reloadFlag;
			this.resourceName = resourceName;
			this.classLoader = classLoader;
		}

		@Override
		public InputStream run() throws IOException {
			InputStream is = null;
			if (reloadFlag) {
				URL url = classLoader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						// Disable caches to get fresh data for reloading.
						connection.setUseCaches(false);
						is = connection.getInputStream();
					}
				}
			} else {
				is = classLoader.getResourceAsStream(resourceName);
			}
			return is;
		}
	}
}
