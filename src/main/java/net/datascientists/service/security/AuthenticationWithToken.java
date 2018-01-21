package net.datascientists.service.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import net.datascientists.vo.TokenResponseVO;

public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date expiry;

	public AuthenticationWithToken(Object aPrincipal, Object aCredentials) {
		super(aPrincipal, aCredentials);
	}

	public AuthenticationWithToken(Object aPrincipal, Object aCredentials,
			Collection<? extends GrantedAuthority> anAuthorities) {
		super(aPrincipal, aCredentials, anAuthorities);
	}

	public void setToken(TokenResponseVO token) {
		setDetails(token);
	}

	public TokenResponseVO getToken() {
		return (TokenResponseVO) getDetails();
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

}
