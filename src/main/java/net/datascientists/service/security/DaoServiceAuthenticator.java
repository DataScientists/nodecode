package net.datascientists.service.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.datascientists.entity.Role;
import net.datascientists.entity.User;
import net.datascientists.service.UserService;
import net.datascientists.vo.TokenResponseVO;

public class DaoServiceAuthenticator implements ExternalServiceAuthenticator {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Override
    public AuthenticatedExternalWebService authenticate(String username, String password) {
    	 AuthenticatedExternalWebService authenticatedExternalWebService = null;
    	 User user = userService.findBySso(username);
        try {
        	if(user!=null && !StringUtils.isEmpty(password)){
        		if(passwordEncoder.matches(password,user.getPassword())){
        			TokenResponseVO tokenResponse = new TokenResponseVO();
            		tokenResponse.getUserInfo().put("roles", getGrantedAuthorities(user));
                	tokenResponse.getUserInfo().put("userId", username);
                	authenticatedExternalWebService = new AuthenticatedExternalWebService(new User(), null,
                			getGrantedAuthorities(user));
                	authenticatedExternalWebService.setToken(tokenResponse);
        		}else{
        			return authenticatedExternalWebService;
        		}
        		
        	}else{
        		return authenticatedExternalWebService;
        	}
        	
        } catch (Exception e) {
			System.out.println("Error during login."+e);
		}

        return authenticatedExternalWebService;
    }  
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(Role userRole : user.getRoles()){
            System.out.println("User log in with role : "+userRole.getName());
            authorities.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        return authorities;
    }
}
