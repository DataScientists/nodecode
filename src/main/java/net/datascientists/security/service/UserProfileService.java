package net.datascientists.security.service;

import java.util.List;

import net.datascientists.security.model.UserProfile;

public interface UserProfileService {

	List<UserProfile> findAll();
    
    UserProfile findByType(String type);
     
    UserProfile findById(int id);
	
}
