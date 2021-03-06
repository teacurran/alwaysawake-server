package com.wirelust.aa.api.helpers;

import java.util.Date;

import com.wirelust.aa.api.v1.representations.AccountType;
import com.wirelust.aa.data.model.Account;

/**
 * Date: 14-03-2015
 *
 * @author T. Curran
 */
public class AccountHelper {

	private AccountHelper() {
		// class is static only
	}

	public static AccountType toRepresentation(Account account) {
		return toRepresentation(account, false);
	}

	public static AccountType toExtendedRepresentation(Account account) {
		return toRepresentation(account, true);
	}

	private static AccountType toRepresentation(Account account, boolean withExtended) {
		if (account == null) {
			return null;
		}

		AccountType at = new AccountType();
		at.setId(account.getId());
		at.setUsername(account.getUsername());
		at.setRealName(account.getFullName());
		at.setAvatar(account.getAvatar());

		if (withExtended) {
			at.setEmail(account.getEmail());
			at.setBackground(account.getBackground());
			at.setBio(account.getBio());
			at.setDateCreated(account.getDateCreated() == null ? null : new Date(account.getDateCreated().getTime()));
			at.setDateModified(account.getDateModified() == null ? null
				: new Date(account.getDateModified().getTime()));
			at.setDateLogin(account.getDateLogin() == null ? null : new Date(account.getDateLogin().getTime()));
			at.setLocation(account.getLocation());
			at.setTimezone(account.getTimezoneId());
			at.setWebsite(account.getWebsite());
			at.setFollowersCount(account.getFollowersCount());
			at.setFollowingCount(account.getFollowingCount());
		}

		return at;
	}
}
