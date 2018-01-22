package net.datascientists.service;

import java.util.List;

import net.datascientists.entity.Role;

public interface UserRoleService {

	List<Role> findAll();
    
	Role findByType(String type);
     
	Role findById(int id);
	
}
