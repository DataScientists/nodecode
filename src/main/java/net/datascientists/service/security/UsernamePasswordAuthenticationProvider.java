package net.datascientists.service.security;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
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
import org.springframework.web.util.UrlPathHelper;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.datascientists.constants.WSConstants;
import net.datascientists.mapper.UserMapper;
import net.datascientists.service.base.BaseService;
import net.datascientists.utilities.PropUtil;
import net.datascientists.vo.HeaderVO;
import net.datascientists.vo.JMXLogVO;
import net.datascientists.vo.RoleVO;
import net.datascientists.vo.TokenResponseVO;
import net.datascientists.vo.UserVO;

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
    @Autowired
    @Qualifier("JMXService")
    private BaseService<JMXLogVO> service;
    private UrlPathHelper urlPathHelper = new UrlPathHelper(); 
    
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
        String tokenForUser = tokenManager.createTokenForUser(username.get(),
            (List<GrantedAuthority>)resultOfAuthentication.getToken().getUserInfo().get("roles"));
        resultOfAuthentication.getToken().setToken(tokenForUser);
        onSuccessfulLoginSaveToJMeter(username, password, tokenForUser);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        return resultOfAuthentication;
    }

    private void onSuccessfulLoginSaveToJMeter(Optional<String> username, Optional<String> password, String tokenForUser)
    {
        if(skipJmeterLogging()){
            return;
        }
        JMXLogVO vo = new JMXLogVO();
        vo.setFunction(WSConstants.LOGIN_URL);
        vo.setDeleted(0);
        List<HeaderVO> list = new ArrayList<>();
        list.add(new HeaderVO(WSConstants.AUTH_USERNAME_PROP,username.get()));
        list.add(new HeaderVO(WSConstants.AUTH_PWD_PROP,password.get()));
        Gson gson = new Gson();
        Type listType = new TypeToken<List<HeaderVO>>() {}.getType();
        String headers = gson.toJson(list,listType);
        vo.setHeader(headers);
        vo.setSessionId(tokenForUser.substring(0, tokenForUser.indexOf("."))+tokenManager.parseUsernameFromToken(tokenForUser)+tokenManager.parseExpiryFromToken(tokenForUser));
        vo.setUserId(username.get());
        vo.setDeleted(0);
        service.save(vo);
    }
    
    private boolean skipJmeterLogging()
    {
        String property = PropUtil.getInstance().getProperty("jmx.skiplogging");
        if(property == null || property.isEmpty() || "false".equals(property)){
            return false;
        }
        return true;
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
