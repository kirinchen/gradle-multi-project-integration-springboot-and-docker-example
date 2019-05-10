package net.surfm.account;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lambdaworks.crypto.SCryptUtil;

/**
 * 
 * @author kirin
 *
 */
public final class ScryptPasswordEncryptor implements PasswordEncoder {


	@Override
	public String encode(CharSequence rawPassword) {
		final StringBuilder sb = new StringBuilder(rawPassword.length());
		sb.append(rawPassword);
		return SCryptUtil.scrypt(sb.toString(), 16384, 8, 1);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		final StringBuilder sb = new StringBuilder(rawPassword.length());
		sb.append(rawPassword);
		return SCryptUtil.check(sb.toString(), encodedPassword);
	}
}