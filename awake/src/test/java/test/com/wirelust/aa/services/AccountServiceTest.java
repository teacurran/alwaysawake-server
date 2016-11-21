package test.com.wirelust.aa.services;

import com.wirelust.aa.data.model.Account;
import com.wirelust.aa.services.AccountService;
import org.junit.Test;

/**
 * Date: 20-Nov-2016
 *
 * @author T. Curran
 */
public class AccountServiceTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnNullAccount() {
		AccountService accountService = new AccountService();
		accountService.setPassword(null, "welcome1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnNullPassword() {
		AccountService accountService = new AccountService();
		accountService.setPassword(new Account(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnEmptyPassword() {
		AccountService accountService = new AccountService();
		accountService.setPassword(new Account(), "");
	}
}
