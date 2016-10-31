package com.wirelust.aa.services;

import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.Asynchronous;
import javax.inject.Named;

/**
 * Date: 6/23/11
 *
 * @author T. Curran
 */
@Named
public class MailService implements Serializable {

	private static final long serialVersionUID = 3572183154201772538L;

	String template;
	String textTemplate;
	HashMap<String, String> replacements = new HashMap<>();

	public void setTemplate(
			final String template) {
		this.template = template;
	}

	public void setTextTemplate(
			final String template) {

		this.textTemplate = template;
	}

	public void setReplacement(
			final String key,
			String value) {

		this.replacements.put(key, value);
	}

	@Asynchronous
	public void sendEmail(
			final String toName,
			final String toEmail,
			final String subject) {

		throw new UnsupportedOperationException("not implemented yet");
	}

	public void sendEmail(
			final String to,
			final String subject) {

		throw new UnsupportedOperationException("not implemented yet");
	}


}
