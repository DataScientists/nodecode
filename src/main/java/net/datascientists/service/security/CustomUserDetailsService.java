package net.datascientists.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datascientists.entity.Role;
import net.datascientists.entity.User;
import net.datascientists.entity.UserRole;
import net.datascientists.service.UserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	 @Autowired
	    private UserService userService;
	     
	    @Transactional(readOnly=true)
	    public UserDetails loadUserByUsername(String ssoId)
	            throws UsernameNotFoundException {
	        User user = userService.findBySso(ssoId);
	        System.out.println("User : "+user);
	        if(user==null){
	            System.out.println("User not found");
	            throw new UsernameNotFoundException("Username not found"); 
	        }
	            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), 
	                 user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
	    }
	 
	     
	    private List<GrantedAuthority> getGrantedAuthorities(User user){
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	         
	        for(Role userRole : user.getRoles()){
	            //System.out.println("UserProfile : "+userProfile);
	            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getName()));
	        }
	        System.out.print("authorities :"+authorities);
	        return authorities;
	    }
	
}
