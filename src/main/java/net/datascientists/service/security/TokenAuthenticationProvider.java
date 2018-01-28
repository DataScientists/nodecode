package net.datascientists.service.security;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import com.google.common.base.Optional;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.RoleVO;
import net.datascientists.vo.TokenResponseVO;
import net.datascientists.vo.UserVO;

public class TokenAuthenticationProvider implements AuthenticationProvider {

	private TokenManager tokenManager;
	
	@Autowired
    @Qualifier("UserService")
    private BaseService<UserVO> userService;

	public TokenAuthenticationProvider(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		@SuppressWarnings("unchecked")
		Optional<String> token = (Optional<String>) authentication.getPrincipal();
		if (!token.isPresent() || token.get().isEmpty()) {
			throw new BadCredentialsException("Invalid token");
		}
		validateToken(token.get());
		String user = tokenManager.parseUsernameFromToken(token.get());
		List<UserVO> list = userService.find("userName",user);
		UserVO userObj = list != null && !list.isEmpty()?list.get(0):null;
		if(userObj == null ){
			return null;
		}
		AuthenticationWithToken authenticatedWithToken = new 
		    AuthenticationWithToken(new UserVO(), null,
						getGrantedAuthorities(userObj));
		TokenResponseVO tokenResponse = new TokenResponseVO();
		tokenResponse.setToken(tokenManager.createTokenForUser(user,
				authenticatedWithToken.getAuthorities()));
		authenticatedWithToken.setToken(tokenResponse);
		authenticatedWithToken.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authenticatedWithToken);
		return authenticatedWithToken;
	}
	
	private void validateToken(String token){
		String parseExpiryFromToken = tokenManager.parseExpiryFromToken(token);
		if(tokenManager.parseUsernameFromToken(token) == null || 
				tokenManager.parseAuthFromToken(token) == null ||
				parseExpiryFromToken == null){
			throw new BadCredentialsException("Invalid token");
		}
		if(ZonedDateTime.now(ZoneId.of("Z")).toLocalDateTime().isAfter(LocalDateTime.parse(parseExpiryFromToken))){
			throw new BadCredentialsException("Token expired");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(UserVO user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for(RoleVO userRole : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getName()));
        }
        return authorities;
    }
}
