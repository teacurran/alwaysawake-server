package com.wirelust.aa.data.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Date: 04-Nov-2016
 *
 * @author T. Curran
 */
public class AccountTest {

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
	}
}
