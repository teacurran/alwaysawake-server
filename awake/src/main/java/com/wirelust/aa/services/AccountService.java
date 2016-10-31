package com.wirelust.aa.services;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.inject.Inject;

import com.wirelust.aa.data.model.Account;
import com.wirelust.aa.data.model.AccountPasswordReset;
import com.wirelust.aa.data.repositories.AccountPasswordResetRepository;
import com.wirelust.aa.exceptions.ServiceException;
import com.wirelust.aa.util.PasswordHash;
import com.wirelust.aa.util.StringUtils;

/**
 * Date: 7/12/12
 *
 * @author T. Curran
 */
public class AccountService implements Serializable {

	private static final long serialVersionUID = -4899150565335105759L;

	@Inject
	private MailService mailService;

	@Inject
	private Configuration configuration;

	@Inject
	AccountPasswordResetRepository accountPasswordResetRepository;

	@Inject
	Messages messages;

	public void setPassword(Account inAccount, String inPassword) {
		if (inAccount == null) {
			return;
		}

		if (inPassword == null || inPassword.length() == 0) {
			throw new IllegalArgumentException("inPassword cannot be empty");
		}

		try {
			PasswordHash ph = new PasswordHash(inPassword);
			inAccount.setPasswordSalt(ph.getSalt());
			inAccount.setPassword(ph.getHash());

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new ServiceException(e);
		}
	}

	public void requestPasswordReset(Account inAccount) {

		if (inAccount == null) {
			return;
		}
		if (StringUtils.isEmpty(inAccount.getEmail())) {
			return;
		}

		AccountPasswordReset passwordReset = new AccountPasswordReset(inAccount);
		passwordReset = accountPasswordResetRepository.save(passwordReset);

		String resetPasswordUrl = configuration.getSetting("email.resetpassword.url");
		resetPasswordUrl = resetPasswordUrl.replaceAll("#KEY#", passwordReset.getUuid());

		mailService.setTemplate("reset-password.html");
		mailService.setReplacement("#NAME#", inAccount.getFullName());
		mailService.setReplacement("#RESET_URL#", resetPasswordUrl);

		mailService.sendEmail(
				inAccount.getFullName(),
				inAccount.getEmail(),
				messages.getMessage("email.passwordreset.subject", "Shortvid - password reset"));
	}

	public boolean checkPassword(Account inAccount, String inPassword) {
		if (inAccount == null || inPassword == null) {
			return false;
		}

		return PasswordHash.check(inPassword, inAccount.getPasswordSalt(), inAccount.getPassword());
	}

}


