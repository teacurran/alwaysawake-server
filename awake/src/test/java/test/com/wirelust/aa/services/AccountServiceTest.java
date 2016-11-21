package test.com.wirelust.aa.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.wirelust.aa.data.model.Account;
import com.wirelust.aa.data.model.AccountPasswordReset;
import com.wirelust.aa.data.repositories.AccountPasswordResetRepository;
import com.wirelust.aa.exceptions.ServiceException;
import com.wirelust.aa.services.AccountService;
import com.wirelust.aa.services.Configuration;
import com.wirelust.aa.services.MailService;
import com.wirelust.aa.util.PasswordHash;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Date: 20-Nov-2016
 *
 * @author T. Curran
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PasswordHash.class)
public class AccountServiceTest {

	@Mock
	AccountPasswordResetRepository accountPasswordResetRepository;

	@Mock
	Configuration configuration;

	@Mock
	MailService mailService;

	@InjectMocks
	AccountService accountService;

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnNullAccount() {
		accountService.setPassword(null, "welcome1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnNullPassword() {
		accountService.setPassword(new Account(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnEmptyPassword() {
		accountService.setPassword(new Account(), "");
	}

	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionIfPasswordHashDoes() throws Exception {
		PowerMockito.whenNew(PasswordHash.class).withArguments(any()).thenThrow(new NoSuchAlgorithmException());

		AccountService accountService = new AccountService();
		accountService.setPassword(new Account(), "welcome1234");
	}

	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionIfPasswordHashDoes2() throws Exception {
		PowerMockito.whenNew(PasswordHash.class).withArguments(any()).thenThrow(new InvalidKeySpecException());

		AccountService accountService = new AccountService();
		accountService.setPassword(new Account(), "welcome1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnNullAccountPasswordReset() {
		accountService.requestPasswordReset(null);
	}

	@Test
	@Ignore
	public void shouldDoNothingWithNoEmailAccountPasswordReset() {

		when(configuration.getSetting(eq("email.resetpassword.url"))).thenReturn("http://127.0.0.1/?#KEY#");
		when(accountPasswordResetRepository.save(any())).then(returnsFirstArg());

		Account account = new Account();
		account.setEmail("none@wirelust.com");

		accountService.requestPasswordReset(account);

		verify(accountPasswordResetRepository).save(any(AccountPasswordReset.class));
	}

}
