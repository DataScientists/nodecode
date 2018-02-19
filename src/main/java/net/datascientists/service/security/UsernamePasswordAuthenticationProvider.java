package net.datascientists.service.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.google.common.base.Optional;
import net.datascientists.mapper.UserMapper;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.TokenResponseVO;
import net.datascientists.vo.UserVO;
import net.datascientists.vo.RoleVO;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private Logger errorLog = LogManager.getLogger("ERRORLOG");
    
    @Autowired
    @Qualifier("UserService")
    private BaseService<UserVO> userService;
    
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenManager tokenManager;
    
    public UsernamePasswordAuthenticationProvider() {
    }

    @SuppressWarnings("unchecked")
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Optional<String> username = (Optional<String>) authentication.getPrincipal();
        Optional<String> password = (Optional<String>) authentication.getCredentials();

        if (!username.isPresent() || !password.isPresent()) {
            errorLog.error("Invalid Domain User Credentials");
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        AuthenticationWithToken resultOfAuthentication = authenticateUser(username.get(), password.get());
        if(resultOfAuthentication == null){
            errorLog.error("Invalid Domain User Credentials");
        	throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        resultOfAuthentication.getToken().setToken(tokenManager.createTokenForUser(username.get(),
        			(List<GrantedAuthority>)resultOfAuthentication.getToken().getUserInfo().get("roles")));
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        return resultOfAuthentication;
    }
    
    private AuthenticationWithToken authenticateUser(String username, String password) {
        AuthenticationWithToken authenticatedWithToken = null;
        List<UserVO> list = userService.find("userName",username);
        UserVO user = list != null && !list.isEmpty()?list.get(0):null;
        try {
            if(user!=null && !StringUtils.isEmpty(password)){
                if(passwordEncoder.matches(password,user.getPassword())){
                    TokenResponseVO tokenResponse = new TokenResponseVO();
                    tokenResponse.getUserInfo().put("roles", getGrantedAuthorities(user));
                    tokenResponse.getUserInfo().put("userId", username);
                    authenticatedWithToken = new AuthenticationWithToken(new UserVO(), null,
                        getGrantedAuthorities(user));
                    authenticatedWithToken.setToken(tokenResponse);
                }else{
                    return authenticatedWithToken;
                }

            }else{
                return authenticatedWithToken;
            }

        } catch (Exception e) {
            System.out.println("Error during login."+e);
        }

        return authenticatedWithToken;
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(UserVO user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(RoleVO userRole : user.getRoles()){
            System.out.println("User log in with role : "+userRole.getName());
            authorities.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        return authorities;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
