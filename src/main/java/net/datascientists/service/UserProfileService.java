package net.datascientists.service;

import java.util.List;

import net.datascientists.entity.UserRole;

public interface UserProfileService {

	List<UserRole> findAll();
    
	UserRole findByType(String type);
     
	UserRole findById(int id);
	
}
