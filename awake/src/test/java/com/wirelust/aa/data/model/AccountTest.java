package com.wirelust.aa.data.model;

import java.time.ZoneId;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 04-Nov-2016
 *
 * @author T. Curran
 */
public class AccountTest {

	public void shouldBeAblToUseGettersAndSetters() {
		Account account = new Account();

		assertFalse(account.isAdmin());
		account.setAdmin(true);
		assertTrue(account.isAdmin());

		assertFalse(account.isDisabled());
		account.setDisabled(true);
		assertTrue(account.isDisabled());

		assertNull(account.getPassword());
		account.setPassword("testp");
		assertEquals("testp", account.getPassword());

		assertNull(account.getPasswordSalt());
		account.setPasswordSalt("1234");
		assertEquals("1234", account.getPasswordSalt());

		assertNull(account.getLocation());
		account.setLocation("Boston");
		assertEquals("Boston", account.getLocation());

		assertNull(account.getEmail());
		account.setEmail("test@wirelust.com");
		assertEquals("test@wirelust.com", account.getEmail());


	}

	@Test
	public void shouldBeAbleToSetStatusId() {
		Account account = new Account();

		account.setStatus(Account.Status.ACTIVE);
		account.prePersist();
		assertEquals(Account.Status.ACTIVE.getValue(), account.getStatusId());

		account.setStatus(Account.Status.DISABLED);
		account.prePersist();
		assertEquals(Account.Status.DISABLED.getValue(), account.getStatusId());

		account.setStatus(Account.Status.EMAIL_VERIFY);
		account.prePersist();
		assertEquals(Account.Status.EMAIL_VERIFY.getValue(), account.getStatusId());

		account.setStatus(Account.Status.INVITED);
		account.prePersist();
		assertEquals(Account.Status.INVITED.getValue(), account.getStatusId());

		account.setStatus(Account.Status.WAITING);
		account.prePersist();
		assertEquals(Account.Status.WAITING.getValue(), account.getStatusId());

		account.setStatus(null);
		account.prePersist();
		assertEquals(Account.Status.WAITING.getValue(), account.getStatusId());
	}

	@Test
	public void shouldBeAbleToSetStatus() {
		Account account = new Account();

		account.setStatusId(Account.Status.ACTIVE.getValue());
		account.postLoad();
		assertEquals(Account.Status.ACTIVE, account.getStatus());

		account.setStatusId(Account.Status.DISABLED.getValue());
		account.postLoad();
		assertEquals(Account.Status.DISABLED, account.getStatus());

		account.setStatusId(Account.Status.EMAIL_VERIFY.getValue());
		account.postLoad();
		assertEquals(Account.Status.EMAIL_VERIFY, account.getStatus());

		account.setStatusId(Account.Status.INVITED.getValue());
		account.postLoad();
		assertEquals(Account.Status.INVITED, account.getStatus());

		account.setStatusId(Account.Status.WAITING.getValue());
		account.postLoad();
		assertEquals(Account.Status.WAITING, account.getStatus());

		account.setStatusId(-100);
		account.postLoad();
		assertNull(account.getStatus());

	}

	@Test
	public void shouldBeAbleToSetDisabledReasonId() {
		Account account = new Account();

		account.setDisabledReason(Account.DisabledReason.GENERIC);
		account.prePersist();
		assertEquals(Account.DisabledReason.GENERIC.getValue(), (int)account.getDisabledReasonId());

		account.setDisabledReason(Account.DisabledReason.EXCESS_PW_FAIL);
		account.prePersist();
		assertEquals(Account.DisabledReason.EXCESS_PW_FAIL.getValue(), (int)account.getDisabledReasonId());

		account.setDisabledReason(Account.DisabledReason.TOS_VIOLATION);
		account.prePersist();
		assertEquals(Account.DisabledReason.TOS_VIOLATION.getValue(), (int)account.getDisabledReasonId());

		account.setDisabledReason(null);
		account.prePersist();
		assertNull(account.getDisabledReasonId());
	}

	@Test
	public void shouldBeAbleToSetDisabledReason() {
		Account account = new Account();

		account.setDisabledReasonId(Account.DisabledReason.GENERIC.getValue());
		account.postLoad();
		assertEquals(Account.DisabledReason.GENERIC, account.getDisabledReason());

		account.setDisabledReasonId(Account.DisabledReason.EXCESS_PW_FAIL.getValue());
		account.postLoad();
		assertEquals(Account.DisabledReason.EXCESS_PW_FAIL, account.getDisabledReason());

		account.setDisabledReasonId(Account.DisabledReason.TOS_VIOLATION.getValue());
		account.postLoad();
		assertEquals(Account.DisabledReason.TOS_VIOLATION, account.getDisabledReason());

		account.setDisabledReasonId(-100);
		account.postLoad();
		assertNull(account.getDisabledReason());
	}

	@Test
	public void shouldBeAbleToSetTimezoneId() {
		Account account = new Account();

		account.setTimezone(ZoneId.of("America/Los_Angeles"));
		account.prePersist();
		assertEquals("America/Los_Angeles", account.getTimezoneId());
	}

	@Test
	public void shouldBeAbleToSetTimezone() {
		Account account = new Account();

		account.setTimezoneId("America/Los_Angeles");
		account.postLoad();
		assertEquals(ZoneId.of("America/Los_Angeles"), account.getTimezone());
	}
}
