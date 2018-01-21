package net.datascientists.service;

import java.util.List;

import net.datascientists.entity.Roles;

public interface UserRoleService {

	List<Roles> findAll();
    
	Roles findByType(String type);
     
	Roles findById(int id);
	
}
